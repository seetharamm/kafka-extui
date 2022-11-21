package com.kafka.dashboard.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.dashboard.entity.TopicEntity;

import com.kafka.dashboard.service.AdminClientServiceImpl;
import com.kafka.dashboard.service.TopicService;

@RestController
@RequestMapping("/topic")
@CrossOrigin(origins = "*")
public class TopicController {
	
	@Autowired 
	TopicService topicService;
	@Autowired
	AdminClientServiceImpl adminClient;
	
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	
	public void setAdminClient(AdminClientServiceImpl adminClient)
	{
		this.adminClient = adminClient;
	}
	public void setTopicService(TopicService topicService)
	{
		this.topicService = topicService;
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> getTopicById(@PathVariable Long topicId) {
		TopicEntity topicEntity = topicService.getTopicById(topicId);
        return new ResponseEntity<>(topicEntity, HttpStatus.OK);

	}
	//@CrossOrigin(origins = "http://localhost:1841")
	@GetMapping("/list")
	private List<TopicEntity> getTopicByIdTest()
	{
		//String ss = adminClient.listMessages("users");
		return topicService.getTopics();
		//return new ResponseEntity<>(topicEntityList.toString(),HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<Object> getAllTopics() {
		adminClient.listConfig();
		Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		//return adminClient.listTopics();
		return new ResponseEntity<>(adminClient.listTopics(),HttpStatus.OK);
		

		
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody TopicEntity topicEntity)
	{
		JSONObject resultObj= new JSONObject();
		String returnData = null;
		String createdTopic;
		//TopicEntity createdTopic = topicService.save(topicEntity);
		try {			
			returnData = adminClient.createTopic(topicEntity);	
			if(returnData != null)
			{
				 createdTopic = topicService.save(topicEntity);
			}
		} catch (Exception e) {
			resultObj.put("ErrorMessage", "Request error : -- "+e.getMessage());
			e.printStackTrace();
		}
		String errorMsg = (String) resultObj.get("ErrorMessage");
		if(errorMsg!=null && !errorMsg.trim().equals("")){
			returnData = resultObj.toJSONString();
		}
		
		return new ResponseEntity<>(returnData,HttpStatus.OK);
	}
	@PutMapping("/update")
	public ResponseEntity<TopicEntity> update(@RequestBody TopicEntity topicEntity)
	{
		TopicEntity updatedTopic = null;
		try {
			updatedTopic = topicService.update(topicEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{name}")
	public ResponseEntity<String> deleteById(@PathVariable("name") String name)
	{
		Collection<String> topicNames = new ArrayList<String>();
		topicNames.add(name);
		try {
			DeleteTopicsResult deleted =  adminClient.deleteTopics(topicNames);
			if(deleted != null)
			{
				topicService.deleteTopic(name);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<>("Record not found",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Topic successfully Deleted",HttpStatus.OK);
	}
	

}
