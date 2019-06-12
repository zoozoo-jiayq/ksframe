package com.jiayq.ks.app.flowconfig;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Transactional
@Service
public class WorkflowTaskService extends BaseServiceProxy<WorkflowTask> {

	@Resource
	private WorkflowTaskDao workflowTaskDao;
	
	@Override
	protected BaseRepository<WorkflowTask> getBaseRepository() {
		// TODO Auto-generated method stub
		return workflowTaskDao;
	}
	
	public List<WorkflowTask> findByInstanceId(String instanceId,Sort sort){
		return workflowTaskDao.findByWorkflowInstanceId(instanceId,sort);
	}
	
	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @return
	 */
	public Page<WorkflowTask> findWaitApprove(String projectId,String userId,Pageable page){
		List<Integer> status = new ArrayList<Integer>();
		status.add(WorkflowTask.TASK_STATUS_ACTIVE);
		return workflowTaskDao.findByProjectIdAndApplyerIdAndStatusIn(projectId, userId, status,page);
	}
	
	public Page<WorkflowTask> findApproveHistory(String projectId,String userId,Pageable page){
		List<Integer> status = new ArrayList<Integer>();
		status.add(WorkflowTask.TASK_STATUS_END);
		status.add(WorkflowTask.TASK_STATUS_ROLLBACK);
		return workflowTaskDao.findByProjectIdAndApplyerIdAndStatusIn(projectId, userId, status,page);
	}
	
	public WorkflowTask findLastTask(String instanceId) {
		return  workflowTaskDao.findTop1ByWorkflowInstanceIdOrderByInserttimeDesc(instanceId);
	}

}
