package com.haroot.home_page.controller;

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

import com.haroot.home_page.logic.DateLogic;
import com.haroot.home_page.model.ArticleData;
import com.haroot.home_page.model.IpProperties;
import com.haroot.home_page.model.QiitaProperties;
import com.haroot.home_page.model.SessionData;
import com.haroot.home_page.logic.MavUtils;

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
		mav = MavUtils.getArticleListMav(mav, jdbcT);
		return mav;
	}

	@RequestMapping("articles/{id}")
	public ModelAndView list(ModelAndView mav, @PathVariable("id") String id) {
		mav = MavUtils.getArticleMav(mav, id, qiitaProperties, jdbcT);
		return mav;
	}

	@RequestMapping("articles/countChange/{id}/{type}")
	@ResponseBody
	public void countChange(@PathVariable("id") String id, @PathVariable("type") String type) {
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

	@RequestMapping("articles/create/{id}")
	public ModelAndView create(ModelAndView mav, HttpServletRequest request, @PathVariable("id") String id) {
		// 自分のみ作成できる
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		if (ipAddress.equals(ipProperties.getIpAddress())) {
			ArticleData articleData = new ArticleData();
			// 既存記事の編集
			if (!id.equals("-1")) {
				Map<String, Object> article = jdbcT.queryForList("SELECT * FROM articles WHERE id=" + id).get(0);
				String title = article.get("title").toString();
				String content = article.get("content").toString();
				articleData.setTitle(title);
				articleData.setContent(content);
			}
			mav.addObject(articleData);
			mav.addObject(id);

			mav.setViewName("contents/articles/create");
		} else {
			// 他は戻す
			//既存記事のページ
			if(!id.equals("-1")) {
				mav = MavUtils.getArticleMav(mav, id, qiitaProperties, jdbcT);
				mav.addObject("errStr", "Sorry, you can't edit articles....");
			}else {
				mav = MavUtils.getArticleListMav(mav, jdbcT);
				mav.addObject("errStr", "Sorry, you can't create articles....");
			}
		}

		return mav;
	}

	@RequestMapping("articles/created/{id}")
	public ModelAndView create(ModelAndView mav, @ModelAttribute @Validated ArticleData articleData,
			@PathVariable("id") String id, BindingResult errResult, HttpServletRequest request,
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

		// 新規記事
		if (id.equals("-1")) {
			String sqlStr = "INSERT INTO articles(title, content, create_date) VALUES(?,?,?)";
			jdbcT.update(sqlStr, title, content, dateStr);
			// 既存記事
		} else {
			String sqlStr = "UPDATE articles SET title=?, content=? WHERE id=" + id;
			jdbcT.update(sqlStr, title, content);
		}

		mav.setViewName("contents/articles/created");

		return mav;
	}
}
