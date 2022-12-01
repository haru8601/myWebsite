package com.haroot.home_page.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.logic.DateLogic;
import com.haroot.home_page.logic.IpLogic;
import com.haroot.home_page.logic.MavUtils;
import com.haroot.home_page.model.ArticleData;
import com.haroot.home_page.model.IpProperties;
import com.haroot.home_page.properties.QiitaProperties;

import lombok.RequiredArgsConstructor;

/**
 * 記事コントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {
    final IpProperties ipProperties;
    final QiitaProperties qiitaProperties;
    final JdbcTemplate jdbcT;

    /**
     * 記事一覧表示
     * 
     * @param mav     MAV
     * @param request リクエスト
     * @return
     */
    @GetMapping
    public ModelAndView articleAll(ModelAndView mav, HttpServletRequest request) {
        String clientIp = IpLogic.getIp(request);
        mav = MavUtils.getArticleListMav(mav, jdbcT, clientIp, ipProperties);
        return mav;
    }

    /**
     * 個別記事表示
     * 
     * @param mav     MAV
     * @param id      記事ID
     * @param request リクエスト
     * @return
     */
    @GetMapping("{id}")
    public ModelAndView article(ModelAndView mav, @PathVariable("id") String id, HttpServletRequest request) {
        mav = MavUtils.getArticleMav(mav, id, qiitaProperties, jdbcT, request, ipProperties);
        return mav;
    }

    /**
     * 記事のいいね数更新
     * 
     * @param id   記事ID
     * @param type 追加か削除か
     */
    @GetMapping("updateCount/{id}/{type}")
    @ResponseBody
    public void updateCount(@PathVariable("id") String id, @PathVariable("type") String type) {
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

    /**
     * 記事登録API
     * 
     * @param mav     MAV
     * @param request リクエスト
     * @param id      記事ID
     * @return
     */
    @GetMapping("create/{id}")
    public ModelAndView registerLink(ModelAndView mav, HttpServletRequest request, @PathVariable("id") String id) {
        // 自分のみ作成できる
        String clientIp = IpLogic.getIp(request);
        if (clientIp.equals(ipProperties.getIpAddress())) {
            ArticleData articleData = new ArticleData();
            // 既存記事の編集
            if (!id.equals("-1")) {
                Map<String, Object> article = jdbcT.queryForList("SELECT * FROM articles WHERE id=" + id).get(0);
                String title = article.get("title").toString();
                String content = article.get("content").toString();
                boolean wip = (int) article.get("wip") == 1;
                articleData.setTitle(title);
                articleData.setContent(content);
                articleData.setWip(wip);
            }
            mav.addObject(articleData);
            mav.addObject(id);

            mav.setViewName("contents/articles/create");
        } else {
            // 他は戻す
            // 既存記事のページ
            if (!id.equals("-1")) {
                mav = MavUtils.getArticleMav(mav, id, qiitaProperties, jdbcT, request, ipProperties);
                mav.addObject("errStr", "Sorry, you can't edit articles....");
            } else {
                mav = MavUtils.getArticleListMav(mav, jdbcT, clientIp, ipProperties);
                mav.addObject("errStr", "Sorry, you can't create articles....");
            }
        }

        return mav;
    }

    /**
     * 記事登録
     * 
     * @param mav           MAV
     * @param articleData   記事フォーム
     * @param bindingResult エラー結果
     * @param id            記事ID
     * @param request       リクエスト
     * @param response      レスポンス
     * @return
     */
    @PostMapping("register/{id}")
    public ModelAndView register(ModelAndView mav,
            @ModelAttribute @Validated ArticleData articleData,
            BindingResult bindingResult,
            @PathVariable("id") String id,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 外部からの侵入禁止
        String clientIp = IpLogic.getIp(request);
        if (!clientIp.equals(ipProperties.getIpAddress())) {
            mav = MavUtils.getArticleListMav(mav, jdbcT, clientIp, ipProperties);
            return mav;
        }
        // エラーがあれば戻る
        if (bindingResult.hasErrors()) {
            mav.addObject(articleData);
            mav.setViewName("contents/articles/create");
            return mav;
        }

        String dateStr = DateLogic.getJSTDateStr();
        String title = articleData.getTitle();
        String content = articleData.getContent();
        boolean wip = articleData.isWip();
        int privateInt = 0;
        if (wip) {
            privateInt = 1;
        }

        // 新規記事
        if (id.equals("-1")) {
            String sqlStr = "INSERT INTO articles(title, content, wip, create_date) VALUES(?,?,?,?)";
            jdbcT.update(sqlStr, title, content, privateInt, dateStr);
            // 既存記事
        } else {
            String sqlStr = "UPDATE articles SET title=?, content=?, wip=?, update_date=? WHERE id=" + id;
            jdbcT.update(sqlStr, title, content, privateInt, dateStr);
        }

        mav.setViewName("contents/articles/created");

        return mav;
    }
}
