package com.jiayq.ks.app.flowconfig;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class WorkflowInstanceService extends BaseServiceProxy<WorkflowInstance> {

	@Resource
	private WorkflowInstanceDao workflowInstanceDao;
	
	@Override
	protected BaseRepository<WorkflowInstance> getBaseRepository() {
		// TODO Auto-generated method stub
		return workflowInstanceDao;
	}

	public Page<WorkflowInstance> findMyApply(String projectId,String userId,Pageable page){
		return workflowInstanceDao.findByProjectIdAndInsertUserId(projectId, userId,page);
	}
}
