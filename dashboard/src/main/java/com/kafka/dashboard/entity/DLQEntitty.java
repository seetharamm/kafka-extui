package com.kafka.dashboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="KBD_DLQ")
public class DLQEntitty {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@Column(name = "consumer_name")
	public String consumerName;
	
	@Column(name = "payload")
	public String payload;
	
	@Column(name = "failed_date")
	public Date failedDate;
	
	@Column(name = "error_msg")
	public String errorMsg;
	
	
	@Column(name = "processed_date")
	public Date processedDate; 
 
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getConsumerName() {
		return consumerName;
	}


	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}


	public String getPayload() {
		return payload;
	}


	public void setPayload(String payload) {
		this.payload = payload;
	}


	public Date getFailedDate() {
		return failedDate;
	}


	public void setFailedDate(Date failedDate) {
		this.failedDate = failedDate;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	


	public Date getProcessedDate() {
		return processedDate;
	}


	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}


	
}
