package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.WorkDetailDto;
import com.haroot.home_page.exception.HarootServerException;
import com.haroot.home_page.service.WorkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("work/{genre}/{name}")
@RequiredArgsConstructor
@Slf4j
public class WorkDetailController {
  private final WorkService workService;

  @GetMapping
  public ModelAndView template(
      @PathVariable String genre,
      @PathVariable String name,
      ModelAndView mav) {
    try {
      WorkDetailDto work = workService.getWithTags(name);
      mav.addObject("work", work);
      mav.setViewName("contents/work/" + work.getGenre().getUrl() + "/" + name);
      return mav;
    } catch (Throwable e) {
      System.err.println("作品詳細の取得に失敗しました.");
      System.err.println(e.getMessage());
      e.printStackTrace();
      throw new HarootServerException("作品詳細の取得に失敗しました.", e);
    }
  }
}
