package com.kafka.dashboard.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.dashboard.entity.ConsumerEntity;
import com.kafka.dashboard.entity.ProducerEntity;
import com.kafka.dashboard.repository.ConsumerRepository;

@Service
public class ConsumerServiceImpl {
	public static final String MESSAGE_TOPIC = "customer-messages";
    public static final String MESSAGE_GROUP = MESSAGE_TOPIC + "-group";
    
    @Autowired
    ConsumerRepository consumerRepository;
    
    @KafkaListener(topics = MESSAGE_TOPIC, groupId = MESSAGE_GROUP)
	    public void onCustomerMessage(String message) throws Exception {
	    	 System.out.println("Message : {}  is received");
	        if (message.contains("Test")) {
	        	 System.out.println( message);
	            throw new RuntimeException("Incompatible message " + message);
	        }
	    }
    public ConsumerEntity getConsumerById(Long id) {
		Optional<ConsumerEntity> consumer = consumerRepository.findById(id);

		if (consumer.isPresent()) {
			return consumer.get();
         }
		return null;
	}
	public List<ConsumerEntity> getConsumers()
	{
		List<ConsumerEntity> consumersList = consumerRepository.findAll();
		if (consumersList.size() > 0)
		 return consumersList;
		else return new ArrayList<ConsumerEntity>();
	}
	public ConsumerEntity save(ConsumerEntity newEntity)
	{
		newEntity = consumerRepository.save(newEntity);
		return newEntity;
	}
	public ConsumerEntity update(ConsumerEntity consumerEntity) throws Exception
	{
		
		Optional<ConsumerEntity> entity = consumerRepository.findById(consumerEntity.getId());
		if(entity.isPresent())
		{
			ConsumerEntity existingEntity =  entity.get();
			existingEntity.setConsumerName(consumerEntity.getConsumerName());
			existingEntity.setRegisterBy(consumerEntity.getRegisterBy());
			existingEntity.setRegisterDate(consumerEntity.getRegisterDate());
			existingEntity.setDeRegisterBy(consumerEntity.getDeRegisterBy());
			existingEntity.setDeRegisterDate(consumerEntity.getDeRegisterDate());
			existingEntity.setModifiedDate(consumerEntity.getModifiedDate());
			existingEntity.setModifiedBy(consumerEntity.getModifiedBy());
			existingEntity.setAppName(consumerEntity.getAppName());
			 return consumerRepository.save(existingEntity);
		} else {
            throw new Exception("No Consumer record exist for given id");
        }
		
		
	}
	public void deleteConsumer(Long id) throws Exception
	{
		Optional<ConsumerEntity> producerEntity = consumerRepository.findById(id);
		if(producerEntity.isPresent())
		{
			consumerRepository.deleteById(id);
		}
		else 
		{
			throw new Exception("No Consumer record exist for given id");
		}
	}
	
	}



