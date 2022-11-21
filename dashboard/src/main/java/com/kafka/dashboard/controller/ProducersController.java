package com.kafka.dashboard.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.dashboard.entity.ProducerEntity;
import com.kafka.dashboard.entity.TopicEntity;
import com.kafka.dashboard.service.ProducerService;
import com.kafka.dashboard.service.TopicService;

@RestController
@RequestMapping("/producer")
@CrossOrigin(origins = "*")
public class ProducersController {

	@Autowired
	ProducerService producerService;

	@GetMapping("/{id}")
	public ResponseEntity<Object> getProducerById(@PathVariable("id") Long id) {
		ProducerEntity producerEntity = producerService.getProducerById(id);
		return new ResponseEntity<>(producerEntity, HttpStatus.OK);

	}

	@GetMapping("/list")
	public List<ProducerEntity> getTopicByIdTest() {
		List<ProducerEntity> ListOfProd = null;
		
		try {
			ListOfProd = producerService.getProducers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ListOfProd;
	}

	@PostMapping("/create")
	public ResponseEntity<ProducerEntity> create(@RequestBody ProducerEntity producerEntity) {
		ProducerEntity createdTopic = producerService.save(producerEntity);
		return new ResponseEntity<>(createdTopic, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ProducerEntity> update(@RequestBody ProducerEntity producerEntity) {
		ProducerEntity updatedProducer = null;
		try {
			 updatedProducer = producerService.update(producerEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(updatedProducer, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
		JSONObject resultObj = new JSONObject();
		String returnData = "";
		try {
			producerService.deleteProducer(id);
		} catch (Exception e) {
			resultObj.put("ErrorMessage", "Deleting producer error : -- " + e.getMessage());
			e.printStackTrace();
		}
		String errorMsg = (String) resultObj.get("ErrorMessage");
		if (errorMsg != null && !errorMsg.trim().equals("")) 
		{
			returnData = resultObj.toJSONString();
		}
		return new ResponseEntity<>("Producer successfully Deleted", HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/publish")
	public ResponseEntity<String> publish(@RequestBody  JSONObject message) {
		JSONObject resultObj = new JSONObject();
		String returnData = "";
		if (message.toString() != null && !message.toString().trim().equals("")) {
           // JSONObject parametersObj = (JSONObject)JSONValue.parse(message);
			String topicName = (String) message.get("topicName");
			try {
				producerService.produceMessage(message.toString(), topicName);
			} catch (Exception e) {
                resultObj.put("ErrorMessage", "Message publish error : -- " + e.getMessage());
				e.printStackTrace();
			}
			String errorMsg = (String) resultObj.get("ErrorMessage");
			if (errorMsg != null && !errorMsg.trim().equals("")) 
			{
				returnData = resultObj.toJSONString();
			}
		}
		return new ResponseEntity<>(returnData, HttpStatus.OK);

	}

}
