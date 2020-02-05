package com.yaqbot.spring.kafka.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DemoDto {

  @NotBlank
  private String title;

  @NotBlank
  private String message;

}
