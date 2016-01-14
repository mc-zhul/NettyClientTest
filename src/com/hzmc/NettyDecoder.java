package com.hzmc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class NettyDecoder extends FrameDecoder {

    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {

        int length = buffer.readableBytes();

        if (length < 1) {
            return null;
        }
        byte[] contents = new byte[length];
        buffer.readBytes(contents);
        return new ClientInfo(JasyptEncryptor.decoder(new String(contents)));
    }
}
