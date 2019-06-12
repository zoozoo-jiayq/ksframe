package com.jiayq.ks.app.consu;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_consu")
public class Consu extends BaseModel {

	private String name;
	private String unit; //计量单位
	@Transient
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
