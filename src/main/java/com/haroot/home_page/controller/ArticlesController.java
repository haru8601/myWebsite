package com.haroot.home_page.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.logic.DateLogic;
import com.haroot.home_page.logic.MavUtils;
import com.haroot.home_page.model.ArticleData;
import com.haroot.home_page.properties.PathProperty;
import com.haroot.home_page.properties.QiitaProperty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 記事コントローラー
 *
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
@Slf4j
public class ArticlesController {
    final QiitaProperty qiitaProperty;
    final JdbcTemplate jdbcT;
    final HttpSession session;
    final PathProperty pathProperty;

    /**
     * 記事一覧表示
     *
     * @param mav MAV
     * @return
     */
    @GetMapping
    public ModelAndView articleAll(ModelAndView mav) {
        mav = MavUtils.getArticleListMav(mav, jdbcT, session);
        return mav;
    }

    /**
     * 個別記事表示
     *
     * @param mav MAV
     * @param id  記事ID
     * @return
     */
    @GetMapping("{id}")
    public ModelAndView article(ModelAndView mav, @PathVariable("id") String id) {
        mav = MavUtils.getArticleMav(mav, id, qiitaProperty, jdbcT, session);
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
     * @param mav MAV
     * @param id  記事ID
     * @return
     */
    @GetMapping("create/{id}")
    public ModelAndView registerLink(ModelAndView mav, @PathVariable("id") String id) {
        // 自分のみ作成できる
        if (session.getAttribute("isLogin") != null) {
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
                mav = MavUtils.getArticleMav(mav, id, qiitaProperty, jdbcT, session);
                mav.addObject("errStr", "Sorry, you can't edit articles....");
            } else {
                mav = MavUtils.getArticleListMav(mav, jdbcT, session);
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
     * @return
     */
    @PostMapping("register/{id}")
    public ModelAndView register(ModelAndView mav,
            @ModelAttribute @Validated ArticleData articleData,
            BindingResult bindingResult,
            @PathVariable("id") String id) {
        // 外部からの侵入禁止
        if (session.getAttribute("isLogin") == null) {
            mav = MavUtils.getArticleListMav(mav, jdbcT, session);
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

    /**
     * 画像アップロード
     * 
     * @param id   記事IDr
     * @param file ファイル
     */
    @PostMapping("/uploadImage/{id}")
    @ResponseBody
    public void uploadImage(@PathVariable("id") String id,
            @RequestParam("imageFile") MultipartFile file) {
        String articleImagePathStr = pathProperty.getResources() + "/static/images/articles" + "/" + id;
        Path articleImagePath = Paths.get(articleImagePathStr);
        // フォルダがなければ作成
        if (Files.notExists(articleImagePath)) {
            try {
                Files.createDirectory(articleImagePath);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
                return;
            }
        }

        // 画像出力
        try (OutputStream os = Files.newOutputStream(
                Paths.get(articleImagePathStr + "/" + file.getOriginalFilename()),
                StandardOpenOption.CREATE)) {
            os.write(file.getBytes());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
