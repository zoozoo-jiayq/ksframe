package com.jiayq.ks.app.projectconsu;

import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ProjectConsuDao extends BaseRepository<ProjectConsu> {

	public ProjectConsu findOneByProjectIdAndConsuId(String projectId,String consuId);
	
}
