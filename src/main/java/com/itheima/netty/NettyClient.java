package com.itheima.netty;

import com.itheima.netty.handler.client.ClientInboundHandler1;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: xuebin
 * @description
 * @Date: 2023/2/7 14:33
 */
@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        nettyClient.start("127.0.0.1", 8888);
    }

    public void start(String host, int port) {
        //创建线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端引导类
            Bootstrap bootstrap = new Bootstrap();
            //配置
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //通服务端建立完成之后
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ClientInboundHandler1());
                        }
                    });

            //连接服务端
            ChannelFuture future = bootstrap.connect(host, port).sync();
            //阻塞等待关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty clinet error , msg={}", e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }

}