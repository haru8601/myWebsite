package com.haroot.home_page.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.logic.DateLogic;
import com.haroot.home_page.model.ArticleData;
import com.haroot.home_page.model.IpProperties;
import com.haroot.home_page.model.QiitaProperties;
import com.haroot.home_page.model.SessionData;

@Controller
@EnableConfigurationProperties({ IpProperties.class, QiitaProperties.class })
public class ArticlesController {
	@Autowired
	SessionData sessionData;
	@Autowired
	IpProperties ipProperties;
	@Autowired
	QiitaProperties qiitaProperties;
	@Autowired
	JdbcTemplate jdbcT;

	@RequestMapping("articles")
	public ModelAndView articles(ModelAndView mav) {
		List<Map<String, Object>> articleList = jdbcT.queryForList("SELECT * FROM articles");
		for (Map<String, Object> content : articleList) {
			String id = content.get("id").toString();
			content.put("id", "articles/" + id);
		}
		mav.addObject("articlesList", articleList);
		mav.addObject("errStr", "");
		mav.setViewName("contents/articles");
		return mav;
	}

	@RequestMapping("articles/{id}")
	public ModelAndView list(ModelAndView mav,
			@PathVariable("id") String id) {
		Map<String, Object> article = jdbcT.queryForList("SELECT * FROM articles WHERE id = " + id).get(0);
		String title = article.get("title").toString();
		int likeCount = Integer.parseInt(article.get("like_count").toString());

		// QiitaAPIから記事一覧を取得
		try {
			URL url = new URL("https://qiita.com/api/v2/users/"
					+ qiitaProperties.getUser() + "/items"
					+ "?token=" + qiitaProperties.getToken());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode nodeList = mapper.readTree(url);
			// 各記事情報取得
			for (JsonNode node : nodeList) {
				String qiitaTitle = node.get("title").toString().replaceAll("\"", "");
				// 同じタイトルのがあったらlike数加算
				if (qiitaTitle.equals(title)) {
					likeCount += node.get("likes_count").asInt();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		mav.addObject("id", id);
		mav.addObject("title", title);
		mav.addObject("content", article.get("content").toString());
		mav.addObject("like_count", likeCount);

		mav.setViewName("contents/articles/template");
		return mav;
	}

	@RequestMapping("articles/countChange/{id}/{type}")
	@ResponseBody
	public void countChange(
			@PathVariable("id") String id,
			@PathVariable("type") String type) {
		String selectStr = "SELECT like_count FROM articles WHERE id=" + id;
		List<Map<String, Object>> articleList = jdbcT.queryForList(selectStr);
		String likeCountStr = articleList.get(0).get("like_count").toString();
		// 記事情報をupdate
		String updateStr = "UPDATE articles SET like_count=? WHERE id=" + id;

		int count = Integer.parseInt(likeCountStr);
		if (type.equals("up")) {
			count++;
		} else if (type.equals("down")) {
			count--;
		}
		// change count
		likeCountStr = String.valueOf(count);
		jdbcT.update(updateStr, likeCountStr);
		return;
	}

	@RequestMapping("articles/create")
	public ModelAndView create(ModelAndView mav, HttpServletRequest request) {
		// 自分のみ作成できる
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		if (ipAddress.equals(ipProperties.getIpAddress())) {
			ArticleData articleData = new ArticleData();
			mav.addObject(articleData);

			mav.setViewName("contents/articles/create");
			// 他は戻す
		} else {
			List<Map<String, Object>> articleList = jdbcT.queryForList("SELECT * FROM articles");
			for (Map<String, Object> content : articleList) {
				String id = content.get("id").toString();
				content.put("id", "articles/" + id);
			}
			mav.addObject("articlesList", articleList);
			mav.addObject("errStr", "Sorry, you can't create articles....");
			mav.setViewName("contents/articles");
		}

		return mav;
	}

	@RequestMapping("articles/created")
	public ModelAndView create(ModelAndView mav,
			@ModelAttribute @Validated ArticleData articleData,
			BindingResult errResult,
			HttpServletRequest request,
			HttpServletResponse response) {
		// エラーがあれば戻る
		if (errResult.hasErrors()) {
			mav.addObject(articleData);
			mav.setViewName("contents/articles/create");
			return mav;
		}

		String dateStr = DateLogic.getJSTDateStr();
		String title = articleData.getTitle();
		String content = articleData.getContent();

		// 記事情報をdbにinsert
		String sqlStr = "INSERT INTO articles(title, content, create_date) VALUES(?,?,?)";
		jdbcT.update(sqlStr, title, content, dateStr);

		mav.setViewName("contents/articles/created");

		return mav;
	}
}
