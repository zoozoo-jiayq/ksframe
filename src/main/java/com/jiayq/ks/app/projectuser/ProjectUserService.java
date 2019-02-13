package com.jiayq.ks.app.projectuser;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class ProjectUserService extends BaseServiceProxy<ProjectUser> {

	@Resource
	private ProjectUserDao projectUserDao;
	
	@Override
	protected BaseRepository<ProjectUser> getBaseRepository() {
		// TODO Auto-generated method stub
		return projectUserDao;
	}
	

}
