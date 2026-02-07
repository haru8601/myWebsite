package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * その他コントローラー
 *
 * @author haroot
 *
 */
@Controller
@RequestMapping("others")
@RequiredArgsConstructor
@Slf4j
public class OthersController {
  private final HttpSession session;

  /**
   * その他一覧表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView others(ModelAndView mav) {
    mav.setViewName("contents/others/index");
    return mav;
  }
}
