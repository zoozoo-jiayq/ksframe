package com.jiayq.ks.app.flowconfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_work_flow_version")
public class WorkFlowVersion extends BaseModel {
	
	public final static int VERSION_STATUS_ENABLE =  100;
	public final static int VERSION_STATUS_DISABLE = 200;

	private String workflowId;
	private String version;
	@Column(name="config",length=5000)
	private String config;
	private int status;
	private String remark;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	
}
