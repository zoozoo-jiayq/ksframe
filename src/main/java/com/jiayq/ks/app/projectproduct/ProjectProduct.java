package com.jiayq.ks.app.projectproduct;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

/**
 * 项目的产出类型，不同的项目产出不同
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_project_product")
public class ProjectProduct extends BaseModel{

	private String projectId;
	private String productId;
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
