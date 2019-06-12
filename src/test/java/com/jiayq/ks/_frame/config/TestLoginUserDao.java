package com.jiayq.ks._frame.config;

import javax.annotation.Resource;

import com.jiayq.ks._frame.security.LoginUserDao;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestLoginUserDao {
	
	@Resource
	private LoginUserDao loginUserDao;
	
//	@Test
	public void testLogin() {
//		LoginUser user = loginUserDao.findOneByUsername("test");
//		Assert.assertTrue(user!=null);
	}
}
