# spring-kafka-demo
This project is a simple demo of
- Spring-Boot
- Kafka
- Docker

There is a simple web-based UI (using Thymelead and Spring MVC) that allows you to enter data in a form.

When you submit the form, the MVC controller receives the data and publishes the DTO on a Kafka topic.

A Spring Kafka listener then receives the DTO and logs a message.

## Requirements
Docker
Java 11
Maven

## Run
Kafka is run in a docker container. To run the docker container, execute the following command:

docker-compose up

Then run the spring-boot application:

mvn spring-boot:run

You can then open the following address in a web browser:

http://localhost:8080/spring-kafka-demo/

and follow the instructions.

## Key Demos
- Spring Kafka configuration in application.yaml
- Kafka topic creation with Spring @Configuration
- Publishing Kafka messages to a topic with Spring KafkaTemplate
- Listening to Kafka messages with Spring @KafkaListener
- Kafka Testing (WIP) with Spring @EmbeddedKafka
