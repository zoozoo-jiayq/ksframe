
package com.jiayq.ks.app.projectuserrole;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Transactional
@Service
public class ProjectUserRoleService extends BaseServiceProxy<ProjectUserRole> {

	@Resource
	private ProjectUserRoleDao projectUserRoleDao;
	
	@Override
	protected BaseRepository<ProjectUserRole> getBaseRepository() {
		// TODO Auto-generated method stub
		return projectUserRoleDao;
	}

	public ProjectUserRole findOneByProjectIdAndUserId(String projectId,String userId) {
		return projectUserRoleDao.findOneByProjectIdAndUserId(projectId, userId);
	}
}
