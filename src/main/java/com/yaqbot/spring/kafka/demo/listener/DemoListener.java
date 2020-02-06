package com.yaqbot.spring.kafka.demo.listener;

import com.yaqbot.spring.kafka.demo.config.KafkaConfiguration;
import com.yaqbot.spring.kafka.demo.dto.DemoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.yaqbot.spring.kafka.demo.config.KafkaConfiguration.TOPIC_DLT;

@Service
@Slf4j
public class DemoListener {

  private static final String FORCE_ERROR_TITLE = "error";

  @KafkaListener(topics = KafkaConfiguration.TOPIC_DEMO)
  public void listen(ConsumerRecord<String, DemoDto> consumerRecord, DemoDto payload) {
    if(FORCE_ERROR_TITLE.equals(payload.getTitle())) {
      throw new RuntimeException(String.format("Forcing exception on message [%s] to demo error handling.", consumerRecord.key()));
    }
    log.info("\n--- RECEIVED MESSAGE [{}] FROM KAFKA ---\n{}\n--- --- ---", consumerRecord.key(), payload);
  }

  @KafkaListener(topics = KafkaConfiguration.TOPIC_DEMO + TOPIC_DLT)
  public void deadLetterListen(ConsumerRecord<String, DemoDto> consumerRecord, DemoDto payload) {
    log.info("\n--- RECEIVED DEAD LETTER MESSAGE [{}] FROM KAFKA ---\n{}\n--- --- ---", consumerRecord.key(), payload.toString());
  }

}
