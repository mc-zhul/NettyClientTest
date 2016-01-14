package com.hzmc;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;


public class NettyClient extends Thread {

	public static boolean	serverFlag		= true;
	public static final int	RECONNECT_DELAY	= 5;
	private final Timer		timer			= new HashedWheelTimer();
	Logger					log4j			= Logger.getLogger(NettyClient.class);
	private String ip;
	
	public NettyClient(String ip) {
		log4j.debug("netty开始初始化");
		this.ip = ip;
	}

	@Override
	public void run() {
		try {
			ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool());
			ClientBootstrap bootstrap = new ClientBootstrap(factory);
			bootstrap.setPipelineFactory(new NettyClientPipelineFactory(bootstrap, timer,ip));
			bootstrap.setOption("tcpNoDelay", true);
			bootstrap.setOption("keepAlive", true);
			bootstrap.setOption("remoteAddress", new InetSocketAddress("172.16.12.2", 9999));
			bootstrap.connect();
			log4j.debug("netty初始化完成");
		} catch (Exception e) {
			log4j.error("错误信息" + "netty初始化失败，关闭程序" + e.getMessage());
			System.exit(0);
		}
	}
}
