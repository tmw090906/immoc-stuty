package com.ccb.tmw.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

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
	private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame)
			throws Exception {
		// 获取客户端传输过来的消息
		String msg = frame.text();
		System.out.println("receive message: " + msg);
		/* 与使用foreach遍历效果一致
		clients.forEach(channel -> {
			channel.writeAndFlush(
					new TextWebSocketFrame(
							"[Server accept msg: ]" + LocalDateTime.now()
							+ ", content:" + msg));
		});*/

		clients.writeAndFlush(
				new TextWebSocketFrame(
						"[Server accept msg: ]" + LocalDateTime.now()
						+ ", content:" + msg));

		
	}

	/**
	 * @description 当客户端连接服务端后, 获取客户端的channel, 并且放到ChannelGroup中
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx)
            throws Exception {
		clients.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx)
            throws Exception {
		// 当触发handlerRemoved后, ChannelGroup会自动移除对应客户端的channel
		/**
		 * client.remove(cxt.channel());
		 */
		System.out.println("shutdown, channel对应的长id为 "
							+ ctx.channel().id().asLongText());
		System.out.println("shutdown, channel对应的短id为 "
							+ ctx.channel().id().asShortText());
	}
	
	

}
