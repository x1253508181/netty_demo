package com.itheima.netty;

import com.itheima.netty.handler.server.MyHttpServerHandler;
import com.itheima.netty.handler.server.ServerInBoundHandler2;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: xuebin
 * @description
 * @Date: 2023/2/7 11:34
 */
@Slf4j
public class NettySever {

    public static void main(String[] args) {
        NettySever nettySever = new NettySever();
        nettySever.start(8888);

    }

    public void start(int port) {
        // 创建线程池
        EventLoopGroup boss = new NioEventLoopGroup(1);//NettyRuntime.availableProcessors() * 2
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerInBoundHandler2 serverInBoundHandler2 = new ServerInBoundHandler2();
        try {
            //创建服务端引导类，引导整个服务端的启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //进行各种配置
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)//设置服务端channel，用于接受连接
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * 方法在每次客户端连接创建完成后为其初始化handler时执行
                         * @param socketChannel
                         * @throws Exception
                         */
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //进自上而下
                            //出自下而上

                            //编写http案例

                            pipeline.addLast(new HttpResponseEncoder());

                            pipeline.addLast(new HttpRequestDecoder());
                            pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 8));

                            pipeline.addLast(new MyHttpServerHandler());

                            //想pipeline添加handler
//                            pipeline.addLast(new ServerOutBoundHandler1());

//                            pipeline.addLast(new ServerInBoundHandler1());
                            //pipeline.addLast(new ServerInBoundHandler2());
//                            pipeline.addLast(serverInBoundHandler2);
//                            pipeline.addLast(new LineBasedFrameDecoder(65536));
//                            byte[] bytes = "@".getBytes(StandardCharsets.UTF_8);
//                            ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
//                             pipeline.addLast(new DelimiterBasedFrameDecoder(65536, byteBuf));

//                            pipeline.addLast(new LengthFieldPrepender(2));
////                            pipeline.addLast(new StringEncoder());
//                            pipeline.addLast(new ProtostuffEncoder());
//
//                            pipeline.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
////                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new ProtostuffDecoder());
//                            pipeline.addLast(new TcpStickHalfHandler1());
                        }
                    });
            //服务端绑定端口启动
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //阻塞，监听服务端端口的关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty server error , msg = {}", e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}