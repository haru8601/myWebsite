package com.haroot.home_page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.model.SessionData;

@Controller
public class AboutController {

	@Autowired
	SessionData sessionData;

	@RequestMapping("about")
	public ModelAndView about(ModelAndView mav) {

		mav.setViewName("contents/about");
		return mav;
	}
}
