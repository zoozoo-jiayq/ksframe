package com.jiayq.ks.app.flowconfig;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface WorkflowInstanceDao extends BaseRepository<WorkflowInstance> {

	public Page<WorkflowInstance> findByProjectIdAndInsertUserId(String projectId,String userId,Pageable page);
}
