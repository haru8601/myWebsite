package com.haroot.home_page.logic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.model.QiitaProperties;

public class MavUtils {
	public static ModelAndView getArticleMav(ModelAndView mav, String id, QiitaProperties qiitaProperties, JdbcTemplate jdbcT) {
		Map<String, Object> article = jdbcT.queryForList("SELECT * FROM articles WHERE id = " + id).get(0);
		String title = article.get("title").toString();
		int likeCount = Integer.parseInt(article.get("like_count").toString());

		// QiitaAPIから記事一覧を取得
		try {
			URL url = new URL("https://qiita.com/api/v2/users/" + qiitaProperties.getUser() + "/items" + "?token="
					+ qiitaProperties.getToken());
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
	
	public static ModelAndView getArticleListMav(ModelAndView mav, JdbcTemplate jdbcT) {
		// 降順(最新から)で取得
		List<Map<String, Object>> articleList = jdbcT.queryForList("SELECT * FROM articles ORDER BY id DESC");
		for (Map<String, Object> content : articleList) {
			String id = content.get("id").toString();
			content.put("id", id);
		}
		mav.addObject("articlesList", articleList);
		mav.addObject("errStr", "");
		mav.setViewName("contents/articles");
		return mav;
	}
}
