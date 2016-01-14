/**
 * 版权所有：美创科技
 * 项目名称:security-client-server
 * 创建者: zoujk
 * 创建日期: 2013-1-6
 * 文件说明:
 * 最近修改者：zoujk
 * 最近修改日期：2013-1-6
 */
package com.hzmc;


import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;


/**
 * netty命令处理类
 * 
 * @author zoujk
 */
public class NettyServerHandler extends IdleStateAwareChannelHandler {

	private static final Logger	log4j	= Logger.getLogger(NettyServerHandler.class);
	Timer timer,timer1;
	
	public NettyServerHandler() {
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, final ChannelStateEvent e) {
		timer = new Timer();
        timer.schedule(new TimerTask() {
        	String s="test";
            public void run() {
                try {
                	ClientInfo info = new ClientInfo(s);
                    e.getChannel().write(info);
                } catch (Exception e) {
                    log4j.error("test：" + e.getMessage());
                }
            }
        }, 0, 1000);
	}

	/**
	 * netty信息接收
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		String address = ctx.getChannel().getRemoteAddress().toString();
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		log4j.error(e.getCause());
		e.getChannel().close();
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		log4j.debug("Netty Server channelClosed, sessionId:" + ctx.getChannel().getId());
	}
}
