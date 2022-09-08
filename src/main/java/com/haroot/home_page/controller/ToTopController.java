package com.haroot.home_page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;

import com.haroot.home_page.logic.IpWriter;
import com.haroot.home_page.model.Constants;
import com.haroot.home_page.model.TopicData;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ToTopController {

	@Autowired
	JdbcTemplate jdbcT;

	@RequestMapping("/")
	public ModelAndView toTop(ModelAndView mav, HttpServletRequest request) {
		String[] DOTIMGS = Constants.DOTIMGS;
		// スタート時のアニメーション用
		Random random = new Random();
		int slotNum = 3;
		String[] slotImg = new String[slotNum];
		for (int i = 0; i < slotNum; i++) {
			int num = random.nextInt(slotNum);
			slotImg[i] = DOTIMGS[num];
		}
		mav.addObject("slotImg", slotImg);

		// クライアントIPアドレス記録(非同期処理)
		IpWriter ipWriter = new IpWriter(request, jdbcT);
		ipWriter.start();

		// トピックリスト取得
		List<TopicData> topicDataList = new ArrayList<>();
		try {
			String topicsFilePath = "static/config/topics.yml";
			InputStream is = new ClassPathResource(topicsFilePath).getInputStream();
			topicDataList = new Yaml().load(is);
			is.close();
		}catch(IOException e) {
			System.out.println("topics.yml load error");
			e.printStackTrace();
		}
		mav.addObject("topicList", topicDataList);

		mav.setViewName("index");
		return mav;
	}

	@RequestMapping("/policy")
	public ModelAndView policy(ModelAndView mav, HttpServletRequest request) {

		mav.setViewName("policy");
		return mav;
	}

	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mav) {
		System.out.println("twitter api test.");
		mav.setViewName("index");
		return mav;
	}
}
