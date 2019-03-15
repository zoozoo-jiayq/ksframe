package com.jiayq.ks.app.feeapply;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_fee_apply")
public class FeeApply extends BaseModel {

	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date applyDate;
	private String applyerId;
	private String applyerName;
	private String feeTypeId;
	private String feeTypeName;
	private String projectId;
	private double money;
	private String remark;
	private String attaches;
	
	@Transient
	private int status;
	
	private String workflowInstanceId;
	
	public String getWorkflowInstanceId() {
		return workflowInstanceId;
	}
	public void setWorkflowInstanceId(String workflowInstanceId) {
		this.workflowInstanceId = workflowInstanceId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getApplyerId() {
		return applyerId;
	}
	public void setApplyerId(String applyerId) {
		this.applyerId = applyerId;
	}
	public String getApplyerName() {
		return applyerName;
	}
	public void setApplyerName(String applyerName) {
		this.applyerName = applyerName;
	}
	public String getFeeTypeId() {
		return feeTypeId;
	}
	public void setFeeTypeId(String feeTypeId) {
		this.feeTypeId = feeTypeId;
	}
	public String getFeeTypeName() {
		return feeTypeName;
	}
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAttaches() {
		return attaches;
	}
	public void setAttaches(String attaches) {
		this.attaches = attaches;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
