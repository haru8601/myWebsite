package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * aboutコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("about")
public class AboutController {

  /**
   * about画面表示
   * 
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView about(ModelAndView mav) {

    mav.setViewName("contents/about");
    return mav;
  }
}
