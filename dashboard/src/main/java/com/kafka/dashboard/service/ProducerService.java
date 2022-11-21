package com.kafka.dashboard.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafka.dashboard.entity.ProducerEntity;
import com.kafka.dashboard.entity.TopicEntity;
import com.kafka.dashboard.repository.ProducerRepository;
import com.kafka.dashboard.repository.TopicRepository;


@Service
public class ProducerService {
	
	
	@Autowired
	ProducerRepository producerRepository;
	
	 private KafkaTemplate<String, String> kafkaTemplate;

	    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
	        this.kafkaTemplate = kafkaTemplate;
	    }
	
	public ProducerEntity getProducerById(Long id) {
		Optional<ProducerEntity> employee = producerRepository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
         }
		return null;
	}
	public List<ProducerEntity> getProducers()
	{
		List<ProducerEntity> producersList = producerRepository.findAll();
		if (producersList.size() > 0)
		 return producersList;
		else return new ArrayList<ProducerEntity>();
	}
	public ProducerEntity save(ProducerEntity newEntity)
	{
		newEntity = producerRepository.save(newEntity);
		return newEntity;
	}
	public ProducerEntity update(ProducerEntity producerEntity) throws Exception
	{
		
		Optional<ProducerEntity> entity = producerRepository.findById(producerEntity.getId());
		if(entity.isPresent())
		{
			ProducerEntity existingEntity =  entity.get();
			existingEntity.setProducerName(producerEntity.getProducerName());
			existingEntity.setRegisterBy(producerEntity.getRegisterBy());
			existingEntity.setRegisterDate(producerEntity.getRegisterDate());
			existingEntity.setDeRegisterBy(producerEntity.getDeRegisterBy());
			existingEntity.setDeRegisterDate(producerEntity.getDeRegisterDate());
			existingEntity.setModifiedDate(producerEntity.getModifiedDate());
			existingEntity.setModifiedBy(producerEntity.getModifiedBy());
			existingEntity.setAppName(producerEntity.getAppName());
			 return producerRepository.save(existingEntity);
		} else {
            throw new Exception("No Topic record exist for given id");
        }
		
		
	}
	public void deleteProducer(Long id) throws Exception
	{
		Optional<ProducerEntity> producerEntity = producerRepository.findById(id);
		if(producerEntity.isPresent())
		{
			producerRepository.deleteById(id);
		}
		else 
		{
			throw new Exception("No Producer record exist for given id");
		}
	}
	

	 public void produceMessage(String message, String topicName) {
	    	 System.out.println("Sending message {} "+message);
	    	 
	        kafkaTemplate.send(topicName, message);
	    }
}
