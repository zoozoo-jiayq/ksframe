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
	
	private String workflowType;
	private String workflowName;
	
	private String workflowVersionId;
	private Integer version;
	
	@Column(name="config",length=5000)
	private String config;
	private int status;

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
