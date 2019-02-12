package com.jiayq.ks._frame.config;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserDao;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestLoginUserDao {
	
	@Resource
	private LoginUserDao loginUserDao;
	
//	@Test
	public void testLogin() {
		LoginUser user = loginUserDao.findOneByUsername("test");
		Assert.assertTrue(user!=null);
	}
}
