package com.haroot.home_page.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * エラーコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
public class MyErrorController implements ErrorController {

    /**
     * エラー画面表示
     * 
     * @param req リクエスト
     * @param mav MAV
     * @return
     */
    @GetMapping("/error")
    public ModelAndView error(HttpServletRequest req, ModelAndView mav) {
        mav.setStatus(HttpStatus.NOT_FOUND);

        mav.setViewName("contents/error");
        return mav;
    }

}
