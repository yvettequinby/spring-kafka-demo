package com.yaqbot.spring.kafka.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfiguration {

  public static final String TOPIC_DEMO = "demo-topic";

  private final ConsumerFactory<Object, Object> kafkaConsumerFactory;
  private final ConcurrentKafkaListenerContainerFactoryConfigurer kafkaListenerContainerFactoryConfigurer;

  @Bean
  public NewTopic demoTopic() {
    return new NewTopic(TOPIC_DEMO, 1, (short) 1);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    kafkaListenerContainerFactoryConfigurer.configure(factory, kafkaConsumerFactory);
    factory.setErrorHandler(new SeekToCurrentErrorHandler()); // will retry 10 times
    return factory;
  }


}
