package com.kafka.dashboard.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import com.kafka.dashboard.service.RetryFailedServiceImpl;




@Component
@ConfigurationProperties
public final class KafkaConfiguration {
  private static final Logger LOG = LoggerFactory.getLogger(KafkaConfiguration.class);
  @Autowired
  RetryFailedServiceImpl retryFailedService;
  

@SuppressWarnings("deprecation")
@Bean
  public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
          ConcurrentKafkaListenerContainerFactoryConfigurer factoryConfigurer,
          ConsumerFactory<Object, Object> kafkaConsumerFactory) {
      ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factoryConfigurer.configure(factory, kafkaConsumerFactory);
      factory.setRetryTemplate(kafkaRetry());
      factory.setRecoveryCallback(retryContext -> {
          ConsumerRecord<?, ?> consumerRecord = (ConsumerRecord<?, ?>) retryContext.getAttribute("record");
          System.out.println( consumerRecord.value());
          retryFailedService.persistFailedMsg(consumerRecord.value().toString());
          return Optional.empty();
      });
      return factory;
  }
  public RetryTemplate kafkaRetry() {
      RetryTemplate retryTemplate = new RetryTemplate();
      FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
      fixedBackOffPolicy.setBackOffPeriod(10 * 1000l);
      retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
      SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
      retryPolicy.setMaxAttempts(3);
      retryTemplate.setRetryPolicy(retryPolicy);
      return retryTemplate;
  }
 

  public void applyCommon(Properties properties) {
    properties.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "http://awsria141.rdcloud.bms.com:9092");
    }
}
