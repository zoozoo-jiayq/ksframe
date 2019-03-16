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

@Component
public class FlowAPI {

	@Resource
	private WorkflowInstanceService instanceService;
	@Resource
	private WorkflowService workflowService;
	@Resource
	private WorkflowVersionService versionService;
	@Resource
	private WorkflowTaskService taskService;
	
	//发起审批
	public WorkflowTask start(String projectId,int type) {
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
		Task first = loadFromConfig(instance.getConfig());
		Task[] currents = new Task[] {first};
		List<WorkflowTask> wfts = loadFromDb(instanceId);
		for(int i=0; i<wfts.size(); i++) {
			WorkflowTask wft = wfts.get(i);
			for(Task t:currents) {
				if(wft.getApplyerId().equals(t.getApproverid()) && wft.getStatus() == WorkflowTask.TASK_STATUS_END) {
					currents = t.getNexts().toArray(new Task[0]);
				}
			}
		}
		if(currents.length >0) {
			Task taskTemplte = currents[0];
			if(taskTemplte.getType().equals("task")) {
				WorkflowTask task = new WorkflowTask();
				task.setWorkflowInstanceId(instanceId);
				task.setApplyerId(taskTemplte.getApproverid());
				task.setApplyerName(taskTemplte.getApprovername());
				task.setStatus(WorkflowTask.TASK_STATUS_ACTIVE);
				task.setProjectId(instance.getProjectId());
				task.setTaskName(taskTemplte.getTaskname());
				task = taskService.save(task);
				return task;
			}
		}
		return null;
	}
	
	
	/**
	 * 构建审批流转关系模板
	 * @param config
	 * @return
	 */
	private Task loadFromConfig(String config){
		Gson gson = new Gson();
		Config c = gson.fromJson(config, Config.class);
		List<Task> tasks = c.getNodes();
		List<TaskSort> sorts = c.getLines();
		for(int i=0; i<sorts.size(); i++) {
			TaskSort ts = sorts.get(i);
			Task from = searchById(tasks, ts.getFrom());
			Task to = searchById(tasks, ts.getTo());
			to.setBefore(from);
		}
		for(int i=0; i<tasks.size(); i++) {
			if(tasks.get(i).getType().equals("begin")) {
				return tasks.get(i).getNexts().get(0);
			}
		}
		return null;
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
	private WorkflowInstance createInstance(String projectId,int type) {
		Workflow flows = workflowService.findByType(projectId, type);
		WorkFlowVersion version = versionService.findActiveFlow(flows.getId());
		
		WorkflowInstance instance = new WorkflowInstance();
		instance.setWorkflowId(flows.getId());
		instance.setWorkflowName(flows.getName());
		instance.setWorkflowVersionId(version.getId());
		instance.setVersion(version.getVersion());
		instance.setConfig(version.getConfig());
		instance.setStatus(WorkflowInstance.INSTANCE_STATUS_ACTIVE);
		instance.setProjectId(projectId);
		instance.setWorkflowType(flows.getType());
		instance = instanceService.save(instance);
		return instance;
	}
	
	//{"nodes":[{"type":"begin","id":"e31ov","position":{"x":100,"y":100}},{"type":"end","id":"lq2o3","position":{"x":645,"y":133}},{"taskname":"财务审批11","approverid":"4028be81690a750f01690a781e9d0002","approvername":"贾永强","id":"ea7i1","type":"task","position":{"x":300,"y":155}}],"lines":[{"from":"e31ov","to":"ea7i1"},{"from":"ea7i1","to":"lq2o3"}]}
	class Config{
		private List<Task> nodes;
		private List<TaskSort> lines;
		public List<Task> getNodes() {
			return nodes;
		}
		public void setNodes(List<Task> nodes) {
			this.nodes = nodes;
		}
		public List<TaskSort> getLines() {
			return lines;
		}
		public void setLines(List<TaskSort> lines) {
			this.lines = lines;
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
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Task other = (Task) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
		private FlowAPI getOuterType() {
			return FlowAPI.this;
		}
		
	}
	
	class TaskSort{
		private String from;
		private String to;
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		
	}
	
}
