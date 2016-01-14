/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hzmc;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 *
 * @author lwj
 * 使用jasypt进行加密和解密
 */
public class JasyptEncryptor {
    /**
     * 加密
     * @param value 传入需要加密的值
     */
    public static String encoder(String value) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("capaa");
        String returnValue = encryptor.encrypt(value);
        return returnValue;
    }
    /**
     * 解密
     * @param 传入需要解密的值
     */
    public static String decoder(String value) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("capaa");
        String returnValue = encryptor.decrypt(value);
        return returnValue;
    }
}
