package com.jiayq.ks.app.fee;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_fee")
public class Fee extends BaseModel {
	
	private String name;
	private String remark;
	
	@Transient
	private int status;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
