package com.yaqbot.spring.kafka.demo.listener;

import com.yaqbot.spring.kafka.demo.config.KafkaTopicConfiguration;
import com.yaqbot.spring.kafka.demo.dto.DemoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class DemoListener {

  @Transactional
  @KafkaListener(topics = KafkaTopicConfiguration.TOPIC_DEMO)
  public void listen(DemoDto payload) {
    log.info("\n--- RECEIVED MESSAGE FROM KAFKA ---\n" + payload + "\n--- --- ---");
  }


}
