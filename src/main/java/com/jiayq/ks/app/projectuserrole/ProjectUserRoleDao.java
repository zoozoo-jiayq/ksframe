package com.jiayq.ks.app.projectuserrole;

import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ProjectUserRoleDao extends BaseRepository<ProjectUserRole> {

	public ProjectUserRole findOneByProjectIdAndUserId(String projectId,String userId);
}
