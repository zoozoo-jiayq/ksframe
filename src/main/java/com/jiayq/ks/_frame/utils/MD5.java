package com.jiayq.ks._frame.utils;
import org.apache.commons.codec.digest.Md5Crypt;

/**
 * MD5加密类
 */
public class MD5 { 
	
    public static String encrypt(String s) {
    	return Md5Crypt.md5Crypt(s.getBytes(),"$1$jiayq123");
   }

}
