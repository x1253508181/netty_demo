package com.itheima.netty.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: xuebin
 * @description
 * @Date: 2023/2/7 16:16
 */
@Slf4j
public class ServerOutBoundHandler1 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("OutboundHandler1----server send msg to client,msg ={}",buf.toString(StandardCharsets.UTF_8));
        super.write(ctx, msg, promise);
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("oh oh oh i am appende".getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(buffer);
    }
}