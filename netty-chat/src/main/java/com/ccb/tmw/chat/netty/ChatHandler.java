package com.ccb.tmw.chat.netty;

import com.ccb.tmw.chat.SpringUtil;
import com.ccb.tmw.chat.enums.MsgActionEnum;
import com.ccb.tmw.chat.model.socket.DataSocketFrame;
import com.ccb.tmw.chat.model.socket.MessageDTO;
import com.ccb.tmw.chat.model.socket.UserChannelRel;
import com.ccb.tmw.chat.service.IUserService;
import com.ccb.tmw.chat.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.transaction.Transactional;

/**
 * @description: 处理消息的Handler
 * @author tmw090906
 * TextWebScoketFrame: 在netty种, 适用于为websocket专门处理文本的对象, frame是消息的载体
 *
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	/**
	 * This attr: 用于记录和管理所有客户端的channel
	 */
	private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


	private IUserService userService = SpringUtil.getBean("userService", IUserService.class);

	@Transactional
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame)
			throws Exception {

		// 1.   获取客户端传输过来的消息
		String msg = frame.text();
		DataSocketFrame dataFrame = JsonUtils.jsonToPojo(msg, DataSocketFrame.class);
		Integer action = dataFrame.getAction();
		// 2.   判断消息的类型,根据不同的消息类型来处理不同的业务
		// 	2.1 当websocket第一次初始化的时候, open 时, 初始化channel ,把用户的userId和channel关联起来
		if (MsgActionEnum.CONNECT.type.equals(action)) {
			String userId = dataFrame.getMessage().getSendUserId();
			Channel currentChannel = ctx.channel();
			UserChannelRel.put(userId, currentChannel);
		} else
		//  2.2 聊天类型的消息, 把聊天记录保存到数据库, 同时标记消息的状态 : (是否签收) [未签收]
		if (MsgActionEnum.CHAT.type.equals(action)) {
            MessageDTO message = dataFrame.getMessage();
            String acceptUserId = message.getAcceptUserId();
            // 保存消息到数据库
            String msgId = userService.saveMsg(message);
            message.setMsgId(msgId);


            Channel acceptChannel = UserChannelRel.get(acceptUserId);
            if (acceptChannel == null) {
                // TODO channel为空时, 用户离线, 推送消息? JPush ,个推, 小米推送
            } else {
                // 当acceptChannel不为空时, 去users(ChannelGroup)中查看是否存在
                Channel findChannel = users.find(acceptChannel.id());
                // 发送消息
                if (findChannel != null) {
                    acceptChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JsonUtils.objectToJson(message)));
                }
            }


		} else
		//  2.3 当类型为签收消息时, 针对具体的消息进行签收, 修改数据库种对应消息的签收状态[已经签收]
		if (MsgActionEnum.SIGNED.type.equals(action)) {
            String[] msgIds = dataFrame.getExtend().split(",");
            for (int i = 0; i < msgIds.length ; i ++) {
            	userService.signMsg(msgIds[i]);
			}
		} else
		//  2.4 心跳监测 1)前端的心跳 2)Netty的心跳
		if (MsgActionEnum.KEEPALIVE.type.equals(action)) {

		}


		
	}

	/**
	 * @description 当客户端连接服务端后, 获取客户端的channel, 并且放到ChannelGroup中
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx)
            throws Exception {
		users.add(ctx.channel());
	}


	/**
	 *
	 * 其实断开后会自动remove , 所以这段代码不用我们自己写
	 * @Override
	 * 	public void handlerRemoved(ChannelHandlerContext ctx){
	 * 		users.remove(ctx);
	 * 	}
	 *
	 * @param ctx
	 */


	/**
	 *
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		// 发生异常之后关闭链接(Channel), 随后移除ctx
		ctx.channel().close();
		users.remove(ctx.channel());
	}
}
