package com.jiayq.ks._frame.security;


import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface LoginUserDao extends BaseRepository<LoginUser> {
	
	public LoginUser findOneByUsername(String username);
}
