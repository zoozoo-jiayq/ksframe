package com.jiayq.ks.app.project;


import javax.persistence.Entity;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@javax.persistence.Table(name="tb_project")
public class Project extends BaseModel{

	private String name;
	private String addr;
	private String remark;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setAddr(String addr){
		this.addr = addr;
	}

	public String getAddr(){
		return this.addr;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}
}