package com.jiayq.ks.app.flowconfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_work_flow_version")
public class WorkFlowVersion extends BaseModel {
	
	public static String TYPE_FEE = "fee";
	public static String TYPE_CONSU = "consu";
	
	private String projectId;
	//fee:费用申请;consu:耗材申请
	private String workflowtype;
	private int version;
	@Column(name="config",length=5000)
	private String config;
	
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getWorkflowtype() {
		return workflowtype;
	}
	public void setWorkflowtype(String workflowtype) {
		this.workflowtype = workflowtype;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
