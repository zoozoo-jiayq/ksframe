package com.jiayq.ks.app.feeapply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface FeeApplyDao extends BaseRepository<FeeApply> {

	public Page<FeeApply> findByApplyerIdAndProjectId(String applyerId,String projectId,Pageable page);
	
	public FeeApply findOneByWorkflowInstanceId(String workflowInstanceId);
}
