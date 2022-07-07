package com.haroot.home_page.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.model.SessionData;

@Controller
public class MyErrorController implements ErrorController {

	@Autowired
	SessionData sessionData;

	@RequestMapping("/error")
	public ModelAndView error(HttpServletRequest req, ModelAndView mav) {
		mav.setStatus(HttpStatus.NOT_FOUND);

		mav.setViewName("contents/error");
		return mav;
	}

}
