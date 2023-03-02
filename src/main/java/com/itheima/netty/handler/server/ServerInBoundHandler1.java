package com.itheima.netty.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: xuebin
 * @description
 * @Date: 2023/2/7 14:21
 */
@Slf4j
public class ServerInBoundHandler1 extends ChannelInboundHandlerAdapter {

    /**
     * 连接已建立好，通道初始化完成
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ServerInboundHandler1 channelActive-----");

        //将事件向下传递
        super.channelActive(ctx);
    }

    /**
     * 读到从channel中读取到数据
     *
     * @param ctx
     * @param msg ButeBuf--->ByteBuffer
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ServerInboundHandler1 channelRead----,remoteAddress={}", ctx.channel().remoteAddress());

        ByteBuf buf = (ByteBuf) msg;

        log.info("ServerInboundHandler1:received client data = {}", buf.toString(StandardCharsets.UTF_8));
        //处理数据
        ctx.fireChannelActive();
        super.channelRead(ctx, msg);
    }

    /**
     * channel数据读取完成
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("ServerInboundHandler1 channelReadComplete----");

        String msg = "hello cline ,i am sever";
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(msg.getBytes(StandardCharsets.UTF_8));

//        ChannelPromise channelPromise = ctx.newPromise();
//        ChannelFuture channelFuture1 = ctx.writeAndFlush(buffer, channelPromise);
//
//        channelPromise.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//
//            }
//        });
//
//        ChannelFuture channelFuture = ctx.writeAndFlush(buffer);
//        channelFuture.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                log.info("write 结束");
//            }
//        });
        ctx.writeAndFlush(buffer);
        super.channelReadComplete(ctx);
    }

    /**
     * 异常回调
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("ServerInboundHandler1 exceptionCaught----,cause={}", cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}