package com.hzmc;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.util.Timer;

/**
 *
 * @author lwj
 */
public class NettyClientPipelineFactory implements ChannelPipelineFactory{

    private Timer timer;
    private ClientBootstrap bootstrap;
    private String ip;
    
    public static final int RECONNECT_DELAY = 5;
    
    public NettyClientPipelineFactory(ClientBootstrap bootstrap,Timer timer,String ip){
        super();
        this.timer = timer;
        this.bootstrap = bootstrap;
        this.ip = ip;
    }
    
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast("frameEncode", new LengthFieldPrepender(4, false));
        pipeline.addLast("encode", new NettyEncoder());
        pipeline.addLast("decode", new NettyDecoder());
        pipeline.addLast("handler", new NettyClientHandler(bootstrap,timer,ip));
        return pipeline;
    }
    
}
