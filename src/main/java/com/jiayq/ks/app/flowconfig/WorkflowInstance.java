package com.jiayq.ks.app.flowconfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_work_flow_instance")
public class WorkflowInstance extends BaseModel {

	public final static int INSTANCE_STATUS_ACTIVE = 100;//审批中
	public final static int INSTANCE_STATUS_END = 200;//结束
	public final static int INSTANCE_STATUS_REJECT = 300;//拒绝申请
	
	private String projectId;
	
	private String workflowId;
	private String workflowName;
	private int workflowType;
	
	private String workflowVersionId;
	private String version;
	
	@Column(name="config",length=5000)
	private String config;
	private int status;

	
	public int getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(int workflowType) {
		this.workflowType = workflowType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getWorkflowVersionId() {
		return workflowVersionId;
	}

	public void setWorkflowVersionId(String workflowVersionId) {
		this.workflowVersionId = workflowVersionId;
	}
	
}
