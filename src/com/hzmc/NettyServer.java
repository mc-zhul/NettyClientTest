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


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;


/**
 * netty 服务端线程
 * 
 * @author zoujk
 */
public class NettyServer extends Thread {

	private int					nettyPort;
	private int					timeout;
	private static final Logger	log4j	= Logger.getLogger(NettyServer.class);

	/**
	 * 启动NETTY
	 * 
	 * @param nettyPort
	 * @param timeout
	 */
	public NettyServer(String nettyPort, String timeout) {
		this.nettyPort = Integer.parseInt(nettyPort);
		this.timeout = Integer.parseInt(timeout);
		this.start();
		log4j.info("Netty Server start!");
	}

	public void run() {
		try {
			ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors
					.newCachedThreadPool(),64);
			ServerBootstrap bootstrap = new ServerBootstrap(factory);
			NettyServerHandler handler = new NettyServerHandler();
			ChannelPipeline pipeline = bootstrap.getPipeline();
			Timer timer = new HashedWheelTimer();
			// 长度设置,最大Integer
			bootstrap.getPipeline().addLast("frameDecoder",
					new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
			bootstrap.getPipeline().addLast("frameEncode", new LengthFieldPrepender(4, false));
			// 超时设置
			pipeline.addLast("timeout", new ReadTimeoutHandler(timer, timeout));
			pipeline.addLast("handler", handler);
			bootstrap.setOption("child.tcpNoDelay", true);
			//child.keepAlive 原来设置是false
			bootstrap.setOption("child.keepAlive", true);
			bootstrap.bind(new InetSocketAddress(nettyPort));
		} catch (Exception e) {
			e.printStackTrace();
			log4j.error("NettyServer.run():" + e.getMessage());
		}
	}
}
