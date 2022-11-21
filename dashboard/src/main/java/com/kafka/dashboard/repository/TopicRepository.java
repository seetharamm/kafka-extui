package com.kafka.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kafka.dashboard.entity.TopicEntity;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity,Long>{

	void deleteById(String name);

	Optional<TopicEntity> findById(Long id);
	
	
	//Long deleteByName(String topicName);
	
	TopicEntity findByTopicName(String topicName);

	

	

}
