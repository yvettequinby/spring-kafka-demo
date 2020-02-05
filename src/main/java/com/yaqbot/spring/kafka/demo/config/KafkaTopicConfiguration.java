package com.yaqbot.spring.kafka.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {

  public static final String TOPIC_DEMO = "demo-topic";

  @Bean
  public NewTopic demoTopic() {
    return new NewTopic(TOPIC_DEMO, 1, (short) 1);
  }


}
