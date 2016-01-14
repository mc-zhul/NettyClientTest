package com.hzmc;

public class Test {

	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			String ip = "245.245.2."+i;
			new NettyClient(ip).start();
		}
	}
}
