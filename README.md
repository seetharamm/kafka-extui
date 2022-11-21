# kafka-extui
kafka-extui
kafka-extui is a web UI interface for viewing Kafka topics, producers and browsing consumer groups. The tool displays information such as brokers, topics, partitions, consumers, and you can view messages. It is in-progress ******************************* It is in-progress

Features For now you can add and delete and View Kafka brokers — topic Consumers and partition assignments.

Create a new topics. Delete topics. get all topics. db persistance. retry mechanism when we have runtime exceptions. email notification when retry failed. DLQ insertion when retry failed. View ACLs.

Requirements Java 8 or newer Kafka (2XXX ) spring-boot 2.7.5

Build mvn clean install

Ui implemented with extjs. you can run both with tomcat or wildify (jboss) (java services and ext ui through war files)
