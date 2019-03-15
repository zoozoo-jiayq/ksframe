package com.jiayq.ks.app.flowconfig;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Transactional
@Service
public class WorkflowVersionService extends BaseServiceProxy<WorkFlowVersion> {

	@Resource
	private WorkflowVersionDao workflowVersionDao;
	
	@Override
	protected BaseRepository<WorkFlowVersion> getBaseRepository() {
		// TODO Auto-generated method stub
		return workflowVersionDao;
	}
	
	public Page<WorkFlowVersion> findByWorkflowId(String workflowId,Pageable page){
		return workflowVersionDao.findByWorkflowId(workflowId,page);
	}
	
	public WorkFlowVersion findActiveFlow(String workflowId) {
		return workflowVersionDao.findOneByWorkflowIdAndStatus(workflowId, WorkFlowVersion.VERSION_STATUS_ENABLE);
	}

}
