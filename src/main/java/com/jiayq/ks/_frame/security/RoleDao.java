package com.jiayq.ks._frame.security;

import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface RoleDao extends BaseRepository<Role> {
	public Role findOneByCode(String code);
	
}
