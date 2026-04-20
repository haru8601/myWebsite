package com.haroot.home_page.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.WorkDetailDto;
import com.haroot.home_page.service.WorkHunterService;
import com.haroot.home_page.service.WorkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * hunterコントローラー
 *
 * @author haroot
 *
 */
@Controller
@RequestMapping("work/game/hunter-script-quiz")
@RequiredArgsConstructor
@Slf4j
public class WorkHunterController {
  private final WorkHunterService workHunterService;
  private final WorkService workService;

  /**
   * ハンター文字クイズ表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView hunterScriptQuiz(ModelAndView mav) {
    WorkDetailDto work = workService.getWithTags("hunter-script-quiz");
    List<String> words = workHunterService.get();
    mav.addObject("work", work);
    mav.addObject("words", words);
    mav.setViewName("contents/work/game/hunter-script-quiz");
    return mav;
  }
}
