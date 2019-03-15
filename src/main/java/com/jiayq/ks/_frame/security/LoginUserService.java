package com.jiayq.ks._frame.security;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;
@Service
@Transactional
public class LoginUserService extends BaseServiceProxy<LoginUser> {

	@Resource
	private LoginUserDao loginUserDao;
	
	@Override
	protected BaseRepository<LoginUser> getBaseRepository() {
		// TODO Auto-generated method stub
		return loginUserDao;
	}

	public Page<LoginUser> findByProjectId(String projectId,Pageable page){
		return loginUserDao.findByProjectId(projectId,page);
	}
}
