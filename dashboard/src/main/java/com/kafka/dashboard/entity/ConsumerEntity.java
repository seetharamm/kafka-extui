package com.kafka.dashboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="KBD_CONSUMERS")
public class ConsumerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@Column(name = "consumer_name")
	public String consumerName;
	
	@Column(name = "register_by")
	public String registerBy;
	
	@Column(name = "register_date")
	public Date registerDate;
	
	@Column(name = "de_register_by")
	public String deRegisterBy;
	
	@Column(name = "de_register_date")
	public Date deRegisterDate;
	
	@Column(name = "app_name")
	public String appName;
	
	@Column(name = "modified_by")
	public String modifiedBy;
	
	@Column(name = "modified_date")
	public Date modifiedDate; 
	
	@Column(name = "consume_from")
	public String consumeFrom;
	
	@Column(name = "end_point")
	public String endPoint; 
	
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

	public String getRegisterBy() {
		return registerBy;
	}

	public void setRegisterBy(String registerBy) {
		this.registerBy = registerBy;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getDeRegisterBy() {
		return deRegisterBy;
	}

	public void setDeRegisterBy(String deRegisterBy) {
		this.deRegisterBy = deRegisterBy;
	}

	public Date getDeRegisterDate() {
		return deRegisterDate;
	}

	public void setDeRegisterDate(Date deRegisterDate) {
		this.deRegisterDate = deRegisterDate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getConsumeFrom() {
		return consumeFrom;
	}

	public void setConsumeFrom(String consumeFrom) {
		this.consumeFrom = consumeFrom;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	
	
	
	
	
	
	
	

}






