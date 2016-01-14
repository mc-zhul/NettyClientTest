package com.hzmc;


import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


public class NettyEncoder extends SimpleChannelHandler {

    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) {
            ClientInfo info = new ClientInfo(e.getMessage().toString());
            ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
            buf.writeBytes((JasyptEncryptor.encoder(info.getValue())).getBytes());
            Channels.write(ctx, e.getFuture(), buf);
    }
}
