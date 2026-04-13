package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("policy/{name}")
@RequiredArgsConstructor
@Slf4j
public class PolicyController {
  @GetMapping
  public ModelAndView template(
      @PathVariable String name,
      ModelAndView mav) {
    mav.setViewName("contents/policy/" + name);
    return mav;
  }
}
