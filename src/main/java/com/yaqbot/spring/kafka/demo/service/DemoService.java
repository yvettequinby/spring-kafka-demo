package com.yaqbot.spring.kafka.demo.service;

import com.yaqbot.spring.kafka.demo.config.KafkaConfiguration;
import com.yaqbot.spring.kafka.demo.dto.DemoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Transactional
  public void sendMessage(DemoDto dto) {
    log.info("Received request to send message to Kafka. Sending now...");
    kafkaTemplate.send(KafkaConfiguration.TOPIC_DEMO, UUID.randomUUID().toString(), dto);
    log.info("Message sent to Kafka");
  }

}
