package com.jiayq.ks._frame.security;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_role")
public class Role extends BaseModel {
	public final static String ADMIN = "admin";//管理员,全部权限
	public final static String PM = "pm";//项目经理，管理员权限-加人
	public final static String FIN = "financial";//财务人员:项目经理权限-报表 
	public final static String BUSINESSER = "businesser";//业务经理（员工），财务人员权限-审批

	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
