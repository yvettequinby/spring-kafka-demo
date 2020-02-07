package com.yaqbot.spring.kafka.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Objects;

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
  public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory() {
    final DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
    final ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(1000);
    backOffPolicy.setMultiplier(1.5);
    final RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(new AlwaysRetryPolicy());
    retryTemplate.setBackOffPolicy(backOffPolicy);
    final ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    kafkaListenerContainerFactoryConfigurer.configure(factory, kafkaConsumerFactory);
    factory.setErrorHandler(new SeekToCurrentErrorHandler(new FixedBackOff(0L, 5L)));
    factory.setRetryTemplate(retryTemplate);
    factory.setStatefulRetry(true);
    factory.setRecoveryCallback(context -> {
      recoverer.accept((ConsumerRecord<?, ?>) Objects.requireNonNull(context.getAttribute("record")),
              (Exception) context.getLastThrowable());
      return null;
    });
    return factory;
  }




}
