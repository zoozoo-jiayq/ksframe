package com.jiayq.ks.app.flowconfig;

import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface WorkflowVersionDao extends BaseRepository<WorkFlowVersion> {

	public WorkFlowVersion findTop1ByProjectIdAndWorkflowtypeOrderByVersionDesc(String projectId,String workflowType);
}
