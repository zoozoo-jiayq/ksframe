
package com.jiayq.ks._frame.security;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class RoleService extends BaseServiceProxy<Role> {

	@Resource
	private RoleDao roleDao;
	
	@Override
	protected BaseRepository<Role> getBaseRepository() {
		// TODO Auto-generated method stub
		return roleDao;
	}

}
