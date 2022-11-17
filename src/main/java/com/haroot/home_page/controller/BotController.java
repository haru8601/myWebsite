package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * botコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/bot")
public class BotController {

    /**
     * bot概要画面
     * 
     * @param mav MAVå
     * @return
     */
    @GetMapping
    public ModelAndView bot(ModelAndView mav) {
        mav.setViewName("contents/bot");
        return mav;
    }
}
