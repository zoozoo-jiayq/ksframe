package com.jiayq.ks.app.flowconfig;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name = "tb_work_flow")
public class Workflow  extends BaseModel{
	
	public static final int FLOW_TYPE_FEE = 100;//费用审批流程

	private String name;
	private String remark;
	private String projectId;
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
