package com.kafka.dashboard.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl {
	
	@Autowired
	private JavaMailSender mailSender;
	public void setMailSender(JavaMailSender mailSender)
	{
		this.mailSender = mailSender;
	}
	
	public void sendmail() throws AddressException, MessagingException, IOException {
	     String subject = "hhhhh";
	String sendFrom = "no-reply@**.com";

	MimeMessagePreparator preparator = new MimeMessagePreparator() {
	@Override
	public void prepare(MimeMessage mimeMessage) {
	MimeMessageHelper message = null;
	try {
	message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	message.setSubject(subject);
	message.setFrom(sendFrom);
	message.setTo(InternetAddress.parse("seetharamaiah.kondapaneni@*****.com"));

	message.setText("emailContent", true);

	} catch (MessagingException e) {
	e.printStackTrace();
	}

	}

				
	};
	mailSender.send(preparator);

	}


}
