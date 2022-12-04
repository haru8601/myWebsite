package com.haroot.home_page.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;

import com.haroot.home_page.logic.IpLogic;
import com.haroot.home_page.model.TopicData;
import com.haroot.home_page.properties.PathProperty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * トップ画面コントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class ToTopController {

    final JdbcTemplate jdbcT;

    final PathProperty pathProperty;

    /**
     * トップ画面表示
     * 
     * @param mav     MAV
     * @param request リクエスト
     * @return
     */
    @GetMapping
    public ModelAndView toTop(ModelAndView mav, HttpServletRequest request) {
        String referer = request.getHeader("REFERER");
        boolean displaySlot = true;
        // 遷移元が自分のサイト内なら(トップページ以外)
        if (referer != null && referer.matches("^https?://" + pathProperty.getSite() + "/.+$")) {
            // slot非表示
            displaySlot = false;
        }
        mav.addObject("displaySlot", displaySlot);

        // クライアントIPアドレス記録(非同期処理)
        IpLogic ipWriter = new IpLogic(request, jdbcT);
        ipWriter.start();

        // トピックリスト取得
        List<TopicData> topicDataList = new ArrayList<>();
        try {
            String topicsFilePath = "static/config/topics.yml";
            InputStream is = new ClassPathResource(topicsFilePath).getInputStream();
            topicDataList = new Yaml().load(is);
            is.close();
        } catch (IOException e) {
            log.error("topics.yml load error");
            e.printStackTrace();
        }
        mav.addObject("topicList", topicDataList);

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
}
