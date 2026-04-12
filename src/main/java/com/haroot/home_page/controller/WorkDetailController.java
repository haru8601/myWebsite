package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.WorkDetailDto;
import com.haroot.home_page.service.WorkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("work/{genre}/{url}")
@RequiredArgsConstructor
@Slf4j
public class WorkDetailController {
  private final WorkService workService;

  @GetMapping
  public ModelAndView template(
      @PathVariable String genre,
      @PathVariable String url,
      ModelAndView mav) {
    try {
      WorkDetailDto work = workService.getWithTags(url);
      mav.addObject("work", work);
      mav.setViewName("contents/work/" + work.getGenre().getUrl() + "/" + url);
      return mav;
    } catch (Throwable e) {
      // TODO: エラーメッセージを渡す
      mav.setViewName("redirect:/work");
      return mav;
    }
  }
}
