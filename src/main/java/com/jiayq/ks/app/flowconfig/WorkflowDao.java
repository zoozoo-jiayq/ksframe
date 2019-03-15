package com.jiayq.ks.app.flowconfig;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface WorkflowDao extends BaseRepository<Workflow> {

	public Page<Workflow> findByProjectId(String projectId,Pageable page);
	
	public Workflow findOneByTypeAndProjectId(int type,String projectId);
}
