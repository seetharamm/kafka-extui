package com.kafka.dashboard.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
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

import com.kafka.dashboard.entity.ConsumerEntity;
import com.kafka.dashboard.entity.ProducerEntity;
import com.kafka.dashboard.service.ConsumerServiceImpl;


@RestController
@RequestMapping("/consumer")
@CrossOrigin(origins = "*")
public class ConsumerController {
	
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	
 
    public static Map<String, Object> props = new HashMap<>();
    
    @Autowired
	ConsumerServiceImpl consumerService;
    
    
    @GetMapping("/{id}")
	public ResponseEntity<Object> getProducerById(@PathVariable("id") Long id) {
    	ConsumerEntity consumerEntity = consumerService.getConsumerById(id);
		return new ResponseEntity<>(consumerEntity, HttpStatus.OK);

	}
    @GetMapping("/list")
	public List<ConsumerEntity> getConsumers() {
		List<ConsumerEntity> ListOfProd = null;
		
		try {
			ListOfProd = consumerService.getConsumers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ListOfProd;
	}

    
    @PostMapping("/create")
	public ResponseEntity<ConsumerEntity> create(@RequestBody ConsumerEntity consumerEntity) {
    	ConsumerEntity result = consumerService.save(consumerEntity);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
    @PutMapping("/update")
	public ResponseEntity<ConsumerEntity> update(@RequestBody ConsumerEntity producerEntity) {
    	ConsumerEntity updatedProducer = null;
		try {
			 updatedProducer = consumerService.update(producerEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(updatedProducer, HttpStatus.OK);

	}

	@SuppressWarnings("unchecked")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
		JSONObject resultObj = new JSONObject();
		String returnData = "";
		try {
			consumerService.deleteConsumer(id);
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

	@GetMapping("/allMessages")
	public void allMessages() {
		//List<TopicPartition> partitions = consumer.partitionsFor(topic).stream().map(p -> new TopicPartition(topic, p.partition()))
			    //.collect(Collectors.toList());
		//adminClient.listMessages();

	}
	
	
	@GetMapping("/messages/{topicName}")
	public Long getTotalNumberOfMessagesInATopic(@PathVariable("topicName") String topicName){
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(getProps());
        List<TopicPartition> partitions = consumer.partitionsFor(topicName).stream()
                .map(p -> new TopicPartition(topicName, p.partition()))
                .collect(Collectors.toList());
        consumer.assign(partitions);
        consumer.seekToEnd(Collections.emptySet());
        Map<TopicPartition, Long> endPartitions = partitions.stream()
                .collect(Collectors.toMap(Function.identity(), consumer::position));
        partitions
		.stream().forEach(e-> System.out.println(e));
        return partitions
        		.stream()
        		.mapToLong(p -> endPartitions.get(p))
        		.sum();
    }
	 public Map<String, Object> getProps() {
	        if (props.isEmpty()) {
	            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	            props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
	            props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");
	        }
	        return props;
	    }

}
