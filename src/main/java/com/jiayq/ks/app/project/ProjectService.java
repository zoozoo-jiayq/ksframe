package com.jiayq.ks.app.project;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class ProjectService extends BaseServiceProxy<Project>{

	@Resource
	private ProjectDao projectDao;

	
	@Override
	protected com.jiayq.ks._frame.base.BaseRepository<Project> getBaseRepository() {
		// TODO Auto-generated method stub
		return projectDao;
	}
	
	public Page<Project> findMyProject(String userId,Pageable page){
		return projectDao.findMyProject(userId,page);
	}
	
}