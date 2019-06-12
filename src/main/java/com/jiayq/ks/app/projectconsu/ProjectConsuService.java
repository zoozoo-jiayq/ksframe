package com.jiayq.ks.app.projectconsu;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class ProjectConsuService extends BaseServiceProxy<ProjectConsu> {

	@Resource
	private ProjectConsuDao projectConsuDao;
	
	@Override
	protected BaseRepository<ProjectConsu> getBaseRepository() {
		// TODO Auto-generated method stub
		return projectConsuDao;
	}

	public ProjectConsu findByProjectIdAndConsuId(String projectId,String consuId) {
		return projectConsuDao.findOneByProjectIdAndConsuId(projectId, consuId);
	}
}
