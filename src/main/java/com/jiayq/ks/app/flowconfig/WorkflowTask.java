package com.jiayq.ks.app.flowconfig;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_work_flow_task")
public class WorkflowTask extends BaseModel {

	public final static int TASK_STATUS_ACTIVE = 100;//处理中
	public final static int TASK_STATUS_END = 200;//结束
	public final static int TASK_STATUS_ROLLBACK = 300;//驳回
	
	private String projectId;
	
	private String workflowInstanceId;
	private String applyerId; //处理人
	private String applyerName; //处理人姓名
	private String taskName;
	private Date processDate;//处理时间
	private int status;
	private String remark;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getWorkflowInstanceId() {
		return workflowInstanceId;
	}
	public void setWorkflowInstanceId(String workflowInstanceId) {
		this.workflowInstanceId = workflowInstanceId;
	}
	public String getApplyerId() {
		return applyerId;
	}
	public void setApplyerId(String applyerId) {
		this.applyerId = applyerId;
	}
	public String getApplyerName() {
		return applyerName;
	}
	public void setApplyerName(String applyerName) {
		this.applyerName = applyerName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
