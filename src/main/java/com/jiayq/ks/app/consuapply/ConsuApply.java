package com.jiayq.ks.app.consuapply;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_consu_apply")
public class ConsuApply extends BaseModel {

	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date applyDate;
	private String applyerId;
	private String applyerName;
	private String consuTypeId;
	private String consuTypeName;
	private String projectId;
	private double nums;
	private String remark;
	private String attaches;
	private String unit;
	
	@Transient
	private int status;
	
	private String workflowInstanceId;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
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
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public String getConsuTypeId() {
		return consuTypeId;
	}
	public void setConsuTypeId(String consuTypeId) {
		this.consuTypeId = consuTypeId;
	}
	public String getConsuTypeName() {
		return consuTypeName;
	}
	public void setConsuTypeName(String consuTypeName) {
		this.consuTypeName = consuTypeName;
	}
	public double getNums() {
		return nums;
	}
	public void setNums(double nums) {
		this.nums = nums;
	}
	
}
