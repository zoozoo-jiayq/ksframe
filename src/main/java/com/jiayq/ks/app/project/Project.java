package com.jiayq.ks.app.project;

import java.persistence.Entity;
import java.persisitence.Table;
import com.jiayq.ks_frame.base.BaseModel;

@Entity
@Table(name="tb_project")
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
		return this.addr;
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