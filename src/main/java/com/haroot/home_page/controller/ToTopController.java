package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.properties.PathProperty;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * トップ画面コントローラー
 *
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ToTopController {

  private final PathProperty pathProperty;

  /**
   * トップ画面表示
   *
   * @param mav     MAV
   * @param request リクエスト
   * @return
   */
  @GetMapping
  public ModelAndView toTop(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
      @CookieValue(name = "visited", required = false) String visitedFlg) {
    String referer = request.getHeader("REFERER");
    boolean displaySlot = false;
    // 遷移元がトップページなら
    if (referer != null && referer.matches("^https?://" + pathProperty.getSite() + "/?$")) {
      // slot表示
      displaySlot = true;
    }
    // 初訪問なら
    if (visitedFlg == null || !visitedFlg.equals("true")) {
      Cookie visitedCookie = new Cookie(
        "visited",
        "true");
      visitedCookie.setMaxAge(60 * 60 * 24 * 365); // 1年間
      response.addCookie(visitedCookie);
      // slot表示
      displaySlot = true;
    }
    mav.addObject("displaySlot", displaySlot);

    mav.addObject("isTop", true);

    mav.setViewName("index");
    return mav;
  }

  /**
   * ポリシー画面表示
   */
  @GetMapping("policy")
  public ModelAndView policy(ModelAndView mav, HttpServletRequest request) {

    mav.setViewName("policy");
    return mav;
  }

  @GetMapping("/twitter-auth")
  public ModelAndView auth(ModelAndView mav) {
    mav.setViewName("error/500");
    return mav;
  }
}
