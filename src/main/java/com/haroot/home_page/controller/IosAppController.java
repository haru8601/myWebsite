package com.haroot.home_page.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.IosAppDto;
import com.haroot.home_page.service.IosAppService;

import lombok.RequiredArgsConstructor;

/**
 * iosアプリコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/ios-app")
@RequiredArgsConstructor
public class IosAppController {

	private final IosAppService iosAppService;

	/**
	 * ios-app画面表示
	 * 
	 * @param mav MAV
	 * @return
	 */
	@GetMapping
	public ModelAndView iosApp(ModelAndView mav) {
		List<IosAppDto> iosAppList = iosAppService.getAll();
		for (IosAppDto content : iosAppList) {
			String url = content.getUrl();
			content.setUrl(url != null ? "ios-app/" + url : "#");
		}
		mav.addObject("iosAppList", iosAppList);

		mav.setViewName("contents/iosApp");
		return mav;
	}

	/**
	 * 個別アプリ画面表示
	 * 
	 * @param mav     MAV
	 * @param appName アプリ名
	 * @return
	 */
	@GetMapping("{appName}")
	public ModelAndView iosPage(ModelAndView mav, @PathVariable String appName) {
		mav.addObject("appName", appName);

		mav.setViewName("contents/iosApp/" + appName);
		return mav;
	}

	/**
	 * 個別アプリポリシー
	 * 
	 * @param mav     MAV
	 * @param appName アプリ名
	 * @return
	 */
	@GetMapping("{appName}/policy")
	public ModelAndView policy(ModelAndView mav, @PathVariable String appName) {
		mav.setViewName("contents/iosApp/" + appName + "/policy");
		return mav;
	}
}
