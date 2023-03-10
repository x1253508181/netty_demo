package com.itheima.netty.handler.client;

import com.itheima.netty.handler.pojo.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: xuebin
 * @description
 * @Date: 2023/2/7 14:52
 */
@Slf4j
public class ClientInboundHandler1 extends ChannelInboundHandlerAdapter {
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("ClientInboundHandler1 channelActive begin send data");
//        //启动，通道准备就绪后向服务端发送数据
//        String message = "hello,i am clinet";
//
//        ByteBuf buf = ctx.alloc().buffer();
//        buf.writeBytes(message.getBytes(StandardCharsets.UTF_8));
////        ByteBuf buf = Unpooled.copiedBuffer(message.getBytes(StandardCharsets.UTF_8));
//        //写数据
////        ctx.channel().writeAndFlush(buf);
//        ctx.writeAndFlush(buf);
//        super.channelActive(ctx);
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ClientInboundHandler1 channelActive begin send data");
        //通道准备就绪后开始向服务端发送数据
        /*ByteBuf buf = Unpooled.copiedBuffer("hello server,i
        am client".getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(buf);*/
        //批量发送数据
        UserInfo userInfo;
        for (int i = 0; i < 100; i++) {
            userInfo = new UserInfo(i, "name" + i, i + 1, (i % 2 == 0) ? "男" : "女", "北京");
//            String s = userInfo.toString() + "@";
            byte[] bytes = userInfo.toString().getBytes(StandardCharsets.UTF_8);
            ByteBuf buffer = ctx.alloc().buffer(bytes.length);
            buffer.writeBytes(bytes);
            ctx.writeAndFlush(userInfo);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ClientInboundHandler1 channelRead----,remoteAddress={}", ctx.channel().remoteAddress());
        ByteBuf buf = (ByteBuf) msg;
        log.info("ClientInboundHandler1:received sever data = {}", buf.toString(StandardCharsets.UTF_8));
        //处理数据
        ctx.fireChannelActive();
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}