package com.itheima.netty.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: xuebin
 * @description
 * @Date: 2023/2/7 14:21
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerInBoundHandler2 extends ChannelInboundHandlerAdapter {

    /**
     * 连接已建立好，通道初始化完成
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ServerInboundHandler2 channelActive-----");

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
        log.info("ServerInboundHandler2 channelRead----,remoteAddress={}", ctx.channel().remoteAddress());

        ByteBuf buf = (ByteBuf) msg;

        log.info("ServerInboundHandler2:received client data = {}", buf.toString(StandardCharsets.UTF_8));
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
        log.info("ServerInboundHandler2 channelReadComplete----");
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
        log.info("ServerInboundHandler2 exceptionCaught----,cause={}", cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}