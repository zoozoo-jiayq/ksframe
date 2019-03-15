package com.jiayq.ks;

import org.junit.Test;

import junit.framework.Assert;

public class TestEncoded {

//	@Test
//	public void test1() {
//		System.out.println(Md5Crypt.md5Crypt("123456".getBytes(),"$1$jiayq123"));
//	}
	
	@Test
	public void test2() {
		String str = "$1$jiayq123$ZgEPKzJkvpiPQyu7Tzsvp/";
		String str2 = "$1$jiayq123$ZgEPKzJkvpiPQyu7Tzsvp/";
		Assert.assertTrue(str.equals(str2));
	}
	
}
