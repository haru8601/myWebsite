package com.haroot.home_page.controller;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

/**
 * iosアプリコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequiredArgsConstructor
public class IOSAppController {

    final JdbcTemplate jdbcT;

    /**
     * ios-app画面表示
     * 
     * @param mav MAV
     * @return
     */
    @GetMapping("/ios-app")
    public ModelAndView iosApp(ModelAndView mav) {
        List<Map<String, Object>> iosAppList = jdbcT.queryForList("select * from iOSapp");
        for (Map<String, Object> content : iosAppList) {
            String url = content.get("URL").toString();
            if (url.equals("")) {
                content.put("URL", "#");
            } else {
                content.put("URL", "ios-app/" + url);
            }
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
    @GetMapping("/ios-app/{appName}")
    public ModelAndView iosPage(
            ModelAndView mav,
            @PathVariable("appName") String appName) {
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
    @GetMapping("/ios-app/{appName}/policy")
    public ModelAndView policy(
            ModelAndView mav,
            @PathVariable("appName") String appName) {
        mav.setViewName("contents/iosApp/" + appName + "/policy");
        return mav;
    }
}
