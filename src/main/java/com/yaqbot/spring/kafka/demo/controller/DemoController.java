package com.yaqbot.spring.kafka.demo.controller;

import com.yaqbot.spring.kafka.demo.dto.DemoDto;
import com.yaqbot.spring.kafka.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class DemoController {

  private final DemoService demoService;

  private static final String TEMPLATE_DEMO = "demo";
  private static final String MODEL_DTO = "dto";
  private static final String MODEL_INFO_MSG = "infoMessage";

  @GetMapping({"", "/"})
  public String goHome(Model model) {
    model.addAttribute(MODEL_DTO, new DemoDto());
    return TEMPLATE_DEMO;
  }

  @PostMapping({"", "/"})
  public String saveFeature(Model model,
                            @Valid @ModelAttribute(MODEL_DTO) DemoDto dto,
                            BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      demoService.sendMessage(dto);
      model.addAttribute(MODEL_INFO_MSG, "Success. Message sent to Kafka.");
      model.addAttribute(MODEL_DTO, new DemoDto());
    }
    return TEMPLATE_DEMO;
  }

}
