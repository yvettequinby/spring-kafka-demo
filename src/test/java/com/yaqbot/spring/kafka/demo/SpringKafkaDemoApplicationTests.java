package com.yaqbot.spring.kafka.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(count = 1, ports = {9093})
class SpringKafkaDemoApplicationTests {

  @Test
  void contextLoads() {
  }

}
