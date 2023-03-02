package com.itheima.netty.codec;

import com.itheima.netty.handler.pojo.UserInfo;
import com.itheima.netty.handler.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author xb
 * @date 2023/3/2 21:26
 */
@Slf4j
public class ProtostuffEncoder extends MessageToMessageEncoder<UserInfo> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UserInfo usr, List<Object> out) throws Exception {
        try {
            byte[] bytes = ProtostuffUtil.doSerialize(usr);
            ByteBuf buffer = ctx.alloc().buffer(bytes.length);
            buffer.writeBytes(buffer);
            out.add(buffer);
        } catch (Exception e) {
            log.error("protostuff encode error,msg={} ", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}