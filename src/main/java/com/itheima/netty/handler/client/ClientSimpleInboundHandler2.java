package com.itheima.netty.handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: xuebin
 * @description
 * @Date: 2023/2/27 16:01
 */
@Slf4j
public class ClientSimpleInboundHandler2 extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("ClientSimpleInboundHandler2 channelRead");
        log.info("ClientSimpleInboundHandler2: received server data = {}", msg.toString(StandardCharsets.UTF_8));
    }
}