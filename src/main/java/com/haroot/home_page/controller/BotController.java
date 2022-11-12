package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BotController {

	@GetMapping("/bot")
	public ModelAndView bot(ModelAndView mav) {
		mav.setViewName("contents/bot");
		return mav;
	}
}
