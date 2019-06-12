package com.jiayq.ks._frame.base;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public class BaseModel {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
	private String id;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date inserttime;
	
	private String insertUserId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInsertUserId() {
		return insertUserId;
	}
	public void setInsertUserId(String insertUserId) {
		this.insertUserId = insertUserId;
	}
	public Date getInserttime() {
		return inserttime;
	}
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	
}
