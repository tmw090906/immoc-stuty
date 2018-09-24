package com.ccb.tmw.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel channel)
                                throws Exception {

                            ChannelPipeline pipeline = channel.pipeline();

                            // websocket 基于 http协议 , 所以要有Http编解码器
                            pipeline.addLast(new HttpServerCodec());

                            // 对写大数据流的支持
                            pipeline.addLast(new ChunkedWriteHandler());

                            // 对HttpMessage进行聚合, 聚合成FullHttpRequest或者FullHttpResponse
                            // 几乎在netty编程中, 都会使用到此Handler
                            pipeline.addLast(new HttpObjectAggregator(1024 * 64));


                            /* ================== 以上用于支持 http 协议   ====================   */

                            /* ================== 以下用于支持 httpWebsocket =================   */
                            /**
                             * websocket 服务器处理的协议, 并且用于指定给客户端链接访问的路由 : /ws
                             * This Handler will 帮你处理一些繁重复杂的事情
                             * And 会帮你处理三次握手动作, handshaking (close, ping , pong) ping + pong = 心跳
                             * for websocket : 都是以 frames 进行传输的 , 不同的数据类型对应的frames也不同
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));


                            pipeline.addLast(new ChatHandler());

                        }

                    });

            ChannelFuture future = bootstrap.bind(8088).sync();

            future.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
