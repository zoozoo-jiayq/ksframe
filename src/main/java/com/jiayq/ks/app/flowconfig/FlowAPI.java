package com.jiayq.ks.app.flowconfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiayq.ks._frame.security.LoginUserService;

@Component
public class FlowAPI {

	@Resource
	private WorkflowInstanceService instanceService;
	@Resource
	private WorkflowVersionService versionService;
	@Resource
	private WorkflowTaskService taskService;
	@Resource
	private LoginUserService loginUserService;
	
	//发起审批
	public WorkflowTask start(String projectId,String type) {
		WorkflowInstance instance = this.createInstance(projectId, type);
		return next(instance.getId());
	}
	
	
	/**
	 * 拒绝
	 * @param taskId
	 */
	public void reject(String taskId,String remark) {
		WorkflowTask task = taskService.findById(taskId).get();
		if(task.getStatus() == WorkflowTask.TASK_STATUS_ACTIVE) {
			task.setStatus(WorkflowTask.TASK_STATUS_ROLLBACK);
			task.setRemark(remark);
			task.setProcessDate(new Date());
			taskService.save(task);
			endInstance(task.getWorkflowInstanceId(),WorkflowInstance.INSTANCE_STATUS_REJECT);
		}else {
			throw new RuntimeException("任务已经处理完毕...");
		}
	}
	
	/**
	 * 审批当前任务，并发起下一个任务
	 * @param taskId
	 */
	public void passTask(String taskId,String remark) {
		WorkflowTask task = taskService.findById(taskId).get();
		if(task.getStatus() == WorkflowTask.TASK_STATUS_ACTIVE) {
			task.setStatus(WorkflowTask.TASK_STATUS_END);
			task.setProcessDate(new Date());
			task.setRemark(remark);
			taskService.save(task);
			WorkflowTask nt = next(task.getWorkflowInstanceId());
			if(nt == null) {
				endInstance(task.getWorkflowInstanceId(),WorkflowInstance.INSTANCE_STATUS_END);
			}
		}else {
			throw new RuntimeException("任务已经处理完毕...");
		}
	}
	
	/**
	 * 查询我发起的申请
	 * @param projectId
	 * @param userId
	 * @return
	 */
	public Page<WorkflowInstance> findMyApply(String projectId,String userId,Pageable page){
		return instanceService.findMyApply(projectId, userId,page);
	}
	
	/**
	 * 待我审批
	 * @param projectId
	 * @param userId
	 * @return
	 */
	public Page<WorkflowTask> findWaitApprove(String projectId,String userId,Pageable page){
		return taskService.findWaitApprove(projectId, userId,page);
	}
	
	/**
	 * 审批历史
	 * @param projectId
	 * @param userId
	 * @return
	 */
	public Page<WorkflowTask> findApproveHistory(String projectId,String userId,Pageable page){
		return  taskService.findApproveHistory(projectId, userId,page);
	}
	
	private void endInstance(String instanceId,int status) {
		WorkflowInstance instance = instanceService.findById(instanceId).get();
		instance.setStatus(status);
		instanceService.save(instance);
	}
	
	/*
	 * next应该支持分支流程，但是目前按照没有分支来实现
	 */
	private WorkflowTask next(String instanceId) {
		WorkflowInstance instance = instanceService.findById(instanceId).get();
		List<Node> templates = loadFromConfig(instance.getConfig());
		WorkflowTask task = taskService.findLastTask(instanceId);
		int nextNode = 0 ;
		if(task!=null) {
			for(int j=0; j<templates.size(); j++) {
				Node t = templates.get(j);
				if(task.getApplyerId().equals(t.getUserid()) ) {
					nextNode = j+1;
					break;
				}
			}
		}
		if(nextNode>=templates.size()) {
			endInstance(instanceId, WorkflowInstance.INSTANCE_STATUS_END);
		}else {
			Node n = templates.get(nextNode);
			WorkflowTask nt = new WorkflowTask();
			nt.setWorkflowInstanceId(instanceId);
			nt.setApplyerId(n.getUserid());
			nt.setApplyerName(n.getUsername());
			nt.setStatus(WorkflowTask.TASK_STATUS_ACTIVE);
			nt.setProjectId(instance.getProjectId());
			nt.setTaskName(n.getUsername());
			nt = taskService.save(nt);
			return nt;
		}
		return null;
	}
	
	
	/**
	 * 构建审批流转关系模板
	 * @param config
	 * @return
	 */
	private List<Node> loadFromConfig(String config){
		Gson gson = new Gson();
		List<Node> c = gson.fromJson(config,new TypeToken<List<Node>>() {}.getType());
		if(c!=null && c.size()>0) {
			c.sort((a,b)->{
				if(a.getIdx() > b.getIdx()) return 1;
				if(a.getIdx() == b.getIdx()) return 0;
				if(a.getIdx() < b.getIdx()) return -1;
				return 0;
			});
		}
		return c;
	}
	
	private Task searchById(List<Task> tasks,String id) {
		for(int i=0; i<tasks.size(); i++) {
			if(tasks.get(i).getId().equals(id)) {
				return tasks.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 构建实际的审批记录
	 * @param instanceId
	 * @return
	 */
	private List<WorkflowTask> loadFromDb(String instanceId){
		List<WorkflowTask> tasks = taskService.findByInstanceId(instanceId,new Sort(Direction.ASC,"inserttime"));
		return tasks;
	}
	
	//创建流程实例
	private WorkflowInstance createInstance(String projectId,String type) {
		WorkFlowVersion version = versionService.findActiveFlow(projectId,type);
		
		WorkflowInstance instance = new WorkflowInstance();
		instance.setWorkflowVersionId(version.getId());
		instance.setVersion(version.getVersion());
		instance.setConfig(version.getConfig());
		instance.setStatus(WorkflowInstance.INSTANCE_STATUS_ACTIVE);
		instance.setProjectId(projectId);
		instance.setWorkflowType(type);
		if(type.equals( WorkFlowVersion.TYPE_FEE)) {
			instance.setWorkflowName("费用申请流程");
		}else if(type.equals( WorkFlowVersion.TYPE_CONSU)) {
			instance.setWorkflowName("耗材申请流程");
		}
		instance = instanceService.save(instance);
		return instance;
	}
	
	class Node{
		private int idx;
		private String userid;
		private String username;
		public int getIdx() {
			return idx;
		}
		public void setIdx(int idx) {
			this.idx = idx;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
	}
	
	class Task{
		private String id;
		private String taskname;
		private String approverid;
		private String approvername;
		private String type;
		private Task before;
		private List<Task> nexts = new ArrayList<>();
		
		public List<Task> getNexts() {
			return nexts;
		}
		public void setNexts(List<Task> nexts) {
			this.nexts = nexts;
		}
		public Task getBefore() {
			return before;
		}
		public void setBefore(Task before) {
			this.before = before;
			if(before.nexts == null) {
				before.nexts = new ArrayList<>();
			}
			before.nexts.add(this);
		}
		public String getTaskname() {
			return taskname;
		}
		public void setTaskname(String taskname) {
			this.taskname = taskname;
		}
		public String getApproverid() {
			return approverid;
		}
		public void setApproverid(String approverid) {
			this.approverid = approverid;
		}
		public String getApprovername() {
			return approvername;
		}
		public void setApprovername(String approvername) {
			this.approvername = approvername;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	
}
