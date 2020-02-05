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

  private static final String FORCE_ERROR_TITLE = "error";

  @KafkaListener(topics = KafkaTopicConfiguration.TOPIC_DEMO)
  public void listen(DemoDto payload) {
    if(FORCE_ERROR_TITLE.equals(payload.getTitle())) {
      throw new RuntimeException("Forcing exception to demo error handling.");
    }
    log.info("\n--- RECEIVED MESSAGE FROM KAFKA ---\n" + payload + "\n--- --- ---");
  }

}
