package com.jiayq.ks._frame.security;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Resource
	private LoginUserDao loginUserDao;
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		LoginUser user = loginUserDao.findOneByUsername(arg0);
		UserDetailsAdapter adapter = new UserDetailsAdapter();
		adapter.setUser(user);
		return adapter;
	}

}
