package com.kafka.dashboard.service;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.dashboard.entity.DLQEntitty;
import com.kafka.dashboard.repository.DLQRepository;

@Service
public class RetryFailedServiceImpl {
	
	@Autowired
	EmailNotificationServiceImpl emailNotifyService;
	@Autowired
	DLQRepository dlqRepository;
	
	public void persistFailedMsg(String payload)
	{
		DLQEntitty dlq = new DLQEntitty();
		dlq.setPayload("jjjjjjjjjj");
		dlq.setConsumerName("kkk");
		dlq.setErrorMsg("Internal server error");
		dlq.setFailedDate(new Date());
		dlqRepository.save(dlq);
		try {
			emailNotifyService.sendmail();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
