package com.jiayq.ks.app.flowconfig;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface WorkflowVersionDao extends BaseRepository<WorkFlowVersion> {

	public Page<WorkFlowVersion> findByWorkflowId(String workflowId,Pageable page);
	
	public WorkFlowVersion findOneByWorkflowIdAndStatus(String workflowId,int status);
}
