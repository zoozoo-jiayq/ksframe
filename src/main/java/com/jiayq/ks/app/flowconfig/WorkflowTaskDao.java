package com.jiayq.ks.app.flowconfig;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface WorkflowTaskDao extends BaseRepository<WorkflowTask> {

	public List<WorkflowTask> findByWorkflowInstanceId(String workflowInstanceId,Sort sort);
	
	public Page<WorkflowTask> findByProjectIdAndApplyerIdAndStatusIn(String projectId,String applyerId,Collection<Integer> status,Pageable page);
	
}
