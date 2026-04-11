package com.haroot.home_page.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.WorkDto;
import com.haroot.home_page.dto.WorkGenreDto;
import com.haroot.home_page.service.WorkService;

import lombok.RequiredArgsConstructor;

/**
 * workコントローラー
 *
 * @author haroot
 *
 */
@Controller
@RequestMapping("work")
@RequiredArgsConstructor
public class WorkController {
  private final WorkService workService;

  /**
   * work画面表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView work(ModelAndView mav) {
    Map<WorkGenreDto, List<WorkDto>> workListMap = workService.getAllWithGenres();

    mav.addObject("workListMap", workListMap);

    mav.setViewName("contents/work/index");
    return mav;
  }
}
