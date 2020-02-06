package com.yaqbot.spring.kafka.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

  public static final String TOPIC_DEMO = "demo-topic";
  public static final String TOPIC_DLT = ".DLT";

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final ConsumerFactory<Object, Object> kafkaConsumerFactory;
  private final ConcurrentKafkaListenerContainerFactoryConfigurer kafkaListenerContainerFactoryConfigurer;

  @Bean
  public NewTopic demoTopic() {
    return TopicBuilder.name(TOPIC_DEMO)
            .partitions(1)
            .replicas(1)
            .compact()
            .build();
  }

  @Bean
  public NewTopic demoDltTopic() {
    return TopicBuilder.name(TOPIC_DEMO + TOPIC_DLT)
            .partitions(1)
            .replicas(1)
            .compact()
            .build();
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    kafkaListenerContainerFactoryConfigurer.configure(factory, kafkaConsumerFactory);
    factory.setErrorHandler(
            new SeekToCurrentErrorHandler( // will retry 10 times
                    new DeadLetterPublishingRecoverer(kafkaTemplate))); // final failure: send to dead letter topic (DLT)
    return factory;
  }


}
