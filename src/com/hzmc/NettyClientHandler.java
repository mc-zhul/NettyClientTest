package com.hzmc;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.util.Timeout;

public class NettyClientHandler extends SimpleChannelHandler {

    Logger log4j = Logger.getLogger(NettyClientHandler.class);
    Timer timer,timer1;
    private org.jboss.netty.util.Timer nettyTimer;
    private ClientBootstrap bootstrap = null;
    String ip;
    
    public NettyClientHandler(ClientBootstrap  bootstrap,org.jboss.netty.util.Timer nettyTimer,String ip){
        this.bootstrap = bootstrap;
        this.nettyTimer = nettyTimer;
        this.ip = ip;
    }
    
    @Override
    public void channelConnected(ChannelHandlerContext ctx, final ChannelStateEvent e) {
    	
//    	String cmd = d.dp();
//    	e.getChannel().write(cmd);
    	
        timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                try {
                	Dialup d = new Dialup();
                	String cmd1 = d.exit();
                	e.getChannel().write(cmd1);
                	String cmd = d.dp(ip);
                	e.getChannel().write(cmd);
                } catch (Exception e) {
                    log4j.error("错误信息：" + e.getMessage());
                }
            }
        }, 0, 1000*60*30);
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        ClientInfo m = (ClientInfo) e.getMessage();
        System.out.println("@@"+e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        Throwable cause = e.getCause();
        log4j.error(cause.getMessage());
        cause.printStackTrace();
        NettyClient.serverFlag = false;
        e.getChannel().close();
        if (timer != null) {
            timer.cancel();
        }
        if(timer1 != null) {
            timer1.cancel();
        }
    }
    
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
        nettyTimer.newTimeout(new org.jboss.netty.util.TimerTask() {
            public void run(Timeout timeout) throws Exception {
                log4j.info("超时："+timeout);
                bootstrap.connect();
            }
        }, NettyClient.RECONNECT_DELAY, TimeUnit.SECONDS);
    }
    
    /**
     * 将字符串1,0改成boolean型的true,false
     * @param status
     * @return 
     */
    public static boolean getBoolean(String status){
        boolean bol = false;
        if("1".equals(status))
            bol = true;
        else if("0".equals(status))
            bol = false;
        return bol;
    }
    
}
