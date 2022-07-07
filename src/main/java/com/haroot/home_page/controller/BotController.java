package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BotController {

	@RequestMapping("/bot")
	public ModelAndView bot(ModelAndView mav) {
		mav.setViewName("contents/bot");
		return mav;
	}
}
