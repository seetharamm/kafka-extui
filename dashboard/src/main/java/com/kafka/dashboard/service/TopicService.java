package com.kafka.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.kafka.dashboard.entity.TopicEntity;
import com.kafka.dashboard.repository.TopicRepository;


@Service
public class TopicService {
	
	
	@Autowired
	TopicRepository topicRepository;
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	public TopicEntity getTopicById(Long id) {
		Optional<TopicEntity> employee = topicRepository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
         }
		return null;
	}
	public void getAllTopics() {
		Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

		AdminClient adminClient = AdminClient.create(properties);

		ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
		listTopicsOptions.listInternal(true);

		try {
			System.out.println(adminClient.listTopics(listTopicsOptions).names().get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<TopicEntity> getTopics()
	{
		List<TopicEntity> topicList = new ArrayList<TopicEntity>();  
		topicRepository.findAll().forEach(student -> topicList.add(student));    
		
		return topicList;
	}
	public String save(TopicEntity newEntity)
	{
		newEntity = topicRepository.save(newEntity);
		return newEntity.toString();
	}
	public TopicEntity update(TopicEntity topicEntity) throws Exception
	{
		
		Optional<TopicEntity> entity = topicRepository.findById(topicEntity.getId());
		if(entity.isPresent())
		{
			TopicEntity existingEntity =  entity.get();
			existingEntity.setCreatedBy(topicEntity.getCreatedBy());
			existingEntity.setCreatedDate(topicEntity.getCreatedDate());
			existingEntity.setModifiedBy(topicEntity.getModifiedBy());
			existingEntity.setModifiedDate(topicEntity.getModifiedDate());
			existingEntity.setTopicDesc(topicEntity.getTopicDesc());
			existingEntity.setTopicName(topicEntity.getTopicName());
			existingEntity.setNumberOfPartitions(topicEntity.getNumberOfPartitions());
			existingEntity.setReplicationFactor(topicEntity.getReplicationFactor());
			 return topicRepository.save(existingEntity);
		} else {
            throw new Exception("No Topic record exist for given id");
        }
		
		
	}
	public void deleteTopic(String name) throws Exception
	{
		TopicEntity topicEntity = topicRepository.findByTopicName(name);
		if(topicEntity != null)		{
			topicRepository.deleteById(topicEntity.getId());
		}
		else 
		
			throw new Exception("No Topic record exist for given id");
		}
	//}
	
}
