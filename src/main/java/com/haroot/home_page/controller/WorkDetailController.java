package com.haroot.home_page.controller;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.WorkDetailDto;
import com.haroot.home_page.exception.HarootNotFoundException;
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
    } catch (NoSuchElementException e) {
      log.error("作品詳細が存在しませんでした. genre: {}, name: {}", genre, name, e);
      log.error(e.getMessage(), e);
      throw new HarootNotFoundException("作品詳細が存在しませんでした.", e);
    }
  }
}
