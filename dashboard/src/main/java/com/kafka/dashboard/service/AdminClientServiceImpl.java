package com.kafka.dashboard.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kafka.dashboard.config.KafkaConfiguration;
import com.kafka.dashboard.entity.TopicEntity;







@Service
public class AdminClientServiceImpl {
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	private static final int POLL_TIMEOUT_MS = 200;
    public static Map<String, Object> props = new HashMap<>();
    
    private KafkaConsumer<byte[], byte[]> kafkaConsumer;

    @SuppressWarnings("unused")
	private final KafkaConfiguration kafkaConfiguration ;

    public AdminClientServiceImpl(KafkaConfiguration kafkaConfiguration) {
      this.kafkaConfiguration = kafkaConfiguration;
    }
    @PostConstruct
    private void initializeClient() {
      if (kafkaConsumer == null) {
        final Properties properties = new Properties();
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "kafdrop-consumer");
        properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        kafkaConfiguration.applyCommon(properties);

        kafkaConsumer = new KafkaConsumer<>(properties);
      }
    }
    
    
    
 /*   private KafkaConsumer<byte[], byte[]> kafkaConsumer;
    //]
   // private final KafkaConfiguration kafkaConfiguration;
    
    @PostConstruct
    private void initializeClient() {
      if (kafkaConsumer == null) {
        final var properties = new Properties();
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "kafdrop-consumer");
        properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        //kafkaConfiguration.applyCommon(properties);

        kafkaConsumer = new KafkaConsumer<>(properties);
      }
    }*/
	public void listConfig()
	{
		  Properties config = new Properties();
	      config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	      AdminClient admin = AdminClient.create(config);
	      try {
			for (Node node : admin.describeCluster().nodes().get()) {
			      System.out.println("-- node: " + node.id() + " --");
			      ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, "0");
			      DescribeConfigsResult dcr = admin.describeConfigs(Collections.singleton(cr));
			      System.out.println("hhhhhhhhhhhhhhhhhh"+dcr.all().get().toString());
			      dcr.all().get().forEach((k, c) -> {
			          c.entries()
			           .forEach(configEntry -> {System.out.println(configEntry.name() + "= " + configEntry.value());});
			      });
			  }
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String listTopics() {
		
		  Properties config = new Properties();
		  String listTopics = null;
	      config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	      AdminClient admin = AdminClient.create(config);
	      try {
	    	  listTopics = admin.listTopics().listings().get().toString();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return listTopics;
	}

	public String createTopic(TopicEntity topicEntity) throws InterruptedException, ExecutionException {
		
		Properties config = new Properties();
		config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		AdminClient admin = AdminClient.create(config);
		// listTopics = admin.listTopics().listings().get().toString();
		NewTopic newTopic = new NewTopic(topicEntity.getTopicName(), 1, (short) 1);
		return newTopic.toString();
	}

	public void listMessages() {
		// TODO Auto-generated method stub
		kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(getProps());
	        List<TopicPartition> partitions = kafkaConsumer.partitionsFor("first_topic").stream()
	                .map(p -> new TopicPartition("first_topic", p.partition()))
	                .collect(Collectors.toList());
	        kafkaConsumer.assign(partitions);
	        kafkaConsumer.seekToBeginning(partitions);
	        
	        //System.out.print(consumer.seekToBeginning(partitions));
	        kafkaConsumer.seekToEnd(Collections.emptySet());
	        Map<TopicPartition, Long> endPartitions = partitions.stream()
	                .collect(Collectors.toMap(Function.identity(), kafkaConsumer::position));
	        partitions
			.stream().forEach(e-> System.out.println(e));
	        
	    }
		 public Map<String, Object> getProps() {
		        if (props.isEmpty()) {
		            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Object.class.getName());
		            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Object.class.getName());
		            props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
		            props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");
		            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
		            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		            props.put(ConsumerConfig.CLIENT_ID_CONFIG, "group-id-json-1");
		            props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
		            
		           
		        }
		        return props;
		    }

	//	 public synchronized String listMessages(String topic) {
		/*	initializeClient();
			//kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<byte[],byte[]>(getProps());
		    final var partitionInfoSet = kafkaConsumer.partitionsFor(topic);
		    final var partitions = partitionInfoSet.stream()
		        .map(partitionInfo -> new TopicPartition(partitionInfo.topic(),
		            partitionInfo.partition()))
		        .collect(Collectors.toList());
		    kafkaConsumer.assign(partitions);
		    final var latestOffsets = kafkaConsumer.endOffsets(partitions);

		    for (var partition : partitions) {
		      final var latestOffset = Math.max(0, latestOffsets.get(partition) - 1);
		      kafkaConsumer.seek(partition, Math.max(0, latestOffset - 100));
		     System.out.println("nnnnnnnnnnnnnnnnnn"+Math.max(0, latestOffset - 100));
		    }
		   // kafkaConsumer.seekToBeginning(partitions);

		    final var totalCount = 100 * partitions.size();
		    final Map<TopicPartition, List<ConsumerRecord<byte[], byte[]>>> rawRecords
		        = partitions.stream().collect(Collectors.toMap(p -> p , p -> new ArrayList<>(100)));
		    System.out.println("partitions.stream()"+partitions.stream().toString());
		    System.out.println("wwwwwwwwwwwwwwwww"+rawRecords.size());
		    var moreRecords = true;
		    while (rawRecords.size() < totalCount && moreRecords) {
		      final var polled = kafkaConsumer.poll(Duration.ofMillis(POLL_TIMEOUT_MS));

		      moreRecords = false;
		      for (var partition : polled.partitions()) {
		        var records = polled.records(partition);
		        System.out.println("recordsssss"+records.size());
		        System.out.println("recordsssss"+records.toString());
		        if (!records.isEmpty()) {
		          rawRecords.get(partition).addAll(records);
		          moreRecords = records.get(records.size() - 1).offset() < latestOffsets.get(partition) - 1;
		        }
		      }
		    }
		   /* return rawRecords
		            .values()
		            .stream()
		            .flatMap(Collection::stream)
		            .map(rec -> new String())
		                
		            .collect(Collectors.toList());*/
		/*    String str = null;
		    for (Entry<TopicPartition, List<ConsumerRecord<byte[], byte[]>>> entry : rawRecords.entrySet()) {
		        System.out.println(entry.getKey() + "/" + entry.getValue());
		        List<ConsumerRecord<byte[], byte[]>> ss = entry.getValue();
		        ConsumerRecord<byte[], byte[]> cc = ss.get(0);
		        System.out.println(cc.partition());
		        System.out.println(cc.offset());
		        System.out.println(cc.timestamp());
		        System.out.println(cc.serializedKeySize());
		        System.out.println(cc.serializedValueSize());
		        System.out.println(cc.partition());
		        byte[] recvalue = cc.value();
		        System.out.println("string " + recvalue);
		        str=new String(recvalue);
		        System.out.println("string " + str);
		    };
		    return str;
		 }
		/*   return rawRecords
		        .values()
		        .stream()
		        .flatMap(Collection::stream)
		        .map(rec -> new ConsumerRecord<>(rec.topic(),
		            rec.partition(),
		            rec.offset(),
		            rec.timestamp(),
		            rec.timestampType(),
		            rec.serializedKeySize(),
		            rec.serializedValueSize(),
		            rec.key(),
		             str,
		            rec.headers(),
		            rec.leaderEpoch()))
		        .collect(Collectors.toList()); 
			
		}*/
		public DeleteTopicsResult deleteTopics(Collection<String> topicNames) throws InterruptedException, ExecutionException {
			Properties config = new Properties();
			  DeleteTopicsResult listTopics = null;
		      config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		      AdminClient admin = AdminClient.create(config);
		      listTopics = admin.deleteTopics(topicNames);
			// TODO Auto-generated method stub
			return listTopics;
		}
		


}
