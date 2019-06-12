package com.jiayq.ks.app.flowconfig;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
	
	public WorkFlowVersion findActiveFlow(String proectId,String workflowtype) {
		return workflowVersionDao.findTop1ByProjectIdAndWorkflowtypeOrderByVersionDesc(proectId,workflowtype);
	}

}
