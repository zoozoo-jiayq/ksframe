package com.jiayq.ks.app.projectconsu;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

/**
 * 项目的产出类型，耗材不同
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_project_consu")
public class ProjectConsu extends BaseModel{

	private String projectId;
	private String consuId;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getConsuId() {
		return consuId;
	}
	public void setConsuId(String consuId) {
		this.consuId = consuId;
	}
	
	
}
