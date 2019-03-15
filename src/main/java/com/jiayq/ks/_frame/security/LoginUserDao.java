package com.jiayq.ks._frame.security;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface LoginUserDao extends BaseRepository<LoginUser> {
	
	public LoginUser findOneByUsername(String username);
	
	public Page<LoginUser> findByProjectId(String projectId,Pageable page);
	
}
