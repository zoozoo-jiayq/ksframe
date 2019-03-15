package com.jiayq.ks.app.productapply;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiayq.ks._frame.base.BaseModel;

@Entity
@Table(name="tb_product_apply")
public class ProductApply extends BaseModel {

	private String projectId;
	private String productId;
	private String productName;
	private String unit;
	private String applyerId;//申报人ID
	private String applyerName;//申报人姓名
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	@JsonFormat(pattern="yyyy-MM-dd")  
	private Date applyDate;//申报日期
	private double amount = 0 ;//申报数量
	private int status;
	
	@Transient
	public String getApplyDateStr() {
		return new SimpleDateFormat("yyyy-MM-dd").format(getApplyDate());
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getApplyerId() {
		return applyerId;
	}
	public void setApplyerId(String applyerId) {
		this.applyerId = applyerId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
