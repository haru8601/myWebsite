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
@RequestMapping("works")
@RequiredArgsConstructor
public class WorkController {
  private final WorkService workService;

  /**
   * works画面表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView works(ModelAndView mav) {
    Map<WorkGenreDto, List<WorkDto>> worksMap = workService.getAllWithGenres();

    mav.addObject("worksMap", worksMap);

    mav.setViewName("contents/works");
    return mav;
  }
}
