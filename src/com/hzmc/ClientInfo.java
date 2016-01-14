package com.hzmc;


/**
 * @author lwj 传输tcp数据时的对象
 */
public class ClientInfo {

	private final String	value;

	public ClientInfo(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
