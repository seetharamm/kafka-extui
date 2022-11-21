package com.kafka.dashboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import org.springframework.data.jpa.repository.Temporal;

@Entity
@Table(name="KBD_TOPICS")
public class TopicEntity {
	
	
	public TopicEntity() {

    }

    public TopicEntity(String topicName, String createdBy, Date createdDate,String modifiedBy, Date modifiedDate,int replicationFactor, int numberOfPartitions) {
        this.topicName = topicName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.numberOfPartitions = numberOfPartitions;
        this.replicationFactor = replicationFactor;
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@Column(name = "topic_name")
	public String topicName;
	
	@Column(name = "created_by")
	public String createdBy;
	
	
	@Column(name = "created_date")
	public Date createdDate;
	
	@Column(name = "topic_desc")
	public String topicDesc;
	
	@Column(name = "modified_by")
	public String modifiedBy;
	
	@Column(name = "modified_date")
	public Date modifiedDate;
	
	@Column(name = "replication_factor")
	public int replicationFactor;
	
	@Column(name = "number_of_partitions")
	public int numberOfPartitions;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getTopicDesc() {
		return topicDesc;
	}

	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
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

	public int getReplicationFactor() {
		return replicationFactor;
	}

	public void setReplicationFactor(int replicationFactor) {
		this.replicationFactor = replicationFactor;
	}

	public int getNumberOfPartitions() {
		return numberOfPartitions;
	}

	public void setNumberOfPartitions(int numberOfPartitions) {
		this.numberOfPartitions = numberOfPartitions;
	}

	
	
	

}
