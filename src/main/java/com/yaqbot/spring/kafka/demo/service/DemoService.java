package com.yaqbot.spring.kafka.demo.service;

import com.yaqbot.spring.kafka.demo.config.KafkaTopicConfiguration;
import com.yaqbot.spring.kafka.demo.dto.DemoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Transactional
  public void sendMessage(DemoDto dto) {
    log.info("Received request to send message to Kafka. Sending now...");
    kafkaTemplate.send(KafkaTopicConfiguration.TOPIC_DEMO, dto);
    log.info("Message sent to Kafka");
  }

}
