package com.haroot.home_page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.logic.IpWriter;
import com.haroot.home_page.model.Constants;
import com.haroot.home_page.model.PathProperties;
import com.haroot.home_page.model.SessionData;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableConfigurationProperties({ PathProperties.class })
public class ToTopController {

	@Autowired
	JdbcTemplate jdbcT;
	@Autowired
	SessionData sessionData;
	@Autowired
	PathProperties pathProperties;

	@RequestMapping("/")
	public ModelAndView toTop(ModelAndView mav, HttpServletRequest request) {
		// トップページ訪問済みフラグ
		boolean visited = sessionData.getVisited();
		mav.addObject("visited", visited);

		String bingoImg = "";
		// 初訪問なら(セッションがなければ)
		if (!visited) {
			String[] DOTIMGS = Constants.DOTIMGS;
			// スタート時のアニメーション用
			Random random = new Random();
			int slotNum = 3;
			String[] slotImg = new String[slotNum];
			int bingoNum = -1;
			for (int i = 0; i < slotNum; i++) {
				int num = random.nextInt(slotNum);
				// ビンゴ数初期化
				if (i == 0) {
					bingoNum = num;
					// 初期値と異なる数値が来たら-1に
				} else if (bingoNum != num) {
					bingoNum = -1;
				}
				slotImg[i] = DOTIMGS[num];
			}
			mav.addObject("slotImg", slotImg);
			// ビンゴならビンゴ画像セット
			if (bingoNum != -1) {
				bingoImg = slotImg[0];
				sessionData.setBingoImg(bingoImg);
			}
			// 訪問情報更新
			sessionData.setVisited(true);

		}else {
			// ビンゴ情報取得
			bingoImg = sessionData.getBingoImg();
		}
		mav.addObject("bingoImg", bingoImg);

		// クライアントIPアドレス記録(非同期処理)
		IpWriter ipWriter = new IpWriter(request, jdbcT);
		ipWriter.start();

		// トピックリスト取得
		File topicsFile = new File(pathProperties.getHomepath() + "/static/config/topics.json");
		List<Map<String, Object>> topicList = new ArrayList<>();
		try {
			// jsonを読み込んでMapのListへキャスト
			topicList = new ObjectMapper().readValue(topicsFile, new TypeReference<List<Map<String, Object>>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		mav.addObject("topicList", topicList);

		mav.addObject("isTop", true);

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
