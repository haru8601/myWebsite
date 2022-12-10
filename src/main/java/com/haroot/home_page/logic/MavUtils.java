package com.haroot.home_page.logic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.exception.HarootNotFoundException;
import com.haroot.home_page.model.ArticleDto;
import com.haroot.home_page.properties.QiitaProperty;

/**
 * MAVクラス
 * 
 * @author sekiharuhito
 *
 */
public class MavUtils {
    /**
     * 記事取得(1件)
     * 
     * @param mav           MAV
     * @param id            記事ID
     * @param qiitaProperty qiitaプロパティ
     * @param jdbcT         jdbcTemplate
     * @param session       セッション
     * @return
     */
    public static ModelAndView getArticleMav(ModelAndView mav, String id, QiitaProperty qiitaProperty,
            JdbcTemplate jdbcT, HttpSession session) {
        int idNum = -1;
        try {
            idNum = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new HarootNotFoundException(ex.getMessage(), ex);
        }
        Map<String, Object> article = jdbcT.queryForList("SELECT * FROM articles WHERE id = " + id).get(0);
        boolean wip = article.get("wip").equals(1);

        // 自分以外かつ非公開なら表示しない
        String title = "この記事は非公開です";
        int likeCount = 0;
        String content = "";

        // 表示する場合
        if (!wip || session.getAttribute("isLogin") != null) {
            title = article.get("title").toString();
            likeCount = Integer.parseInt(article.get("like_count").toString());
            content = article.get("content").toString();

            // QiitaAPIから記事一覧を取得
            try {
                URL url = new URL("https://qiita.com/api/v2/users/" + qiitaProperty.getUser() + "/items" + "?token="
                        + qiitaProperty.getToken());
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
        }
        mav.addObject(new ArticleDto(idNum, title, content, likeCount, wip));
        mav.setViewName("contents/articles/template");
        return mav;
    }

    /**
     * 記事取得(複数)
     * 
     * @param mav     MAV
     * @param jdbcT   jdbcTemplate
     * @param session セッション
     * @return
     */
    public static ModelAndView getArticleListMav(ModelAndView mav, JdbcTemplate jdbcT, HttpSession session) {
        String whereStr = "";
        // 自分以外のIPは非公開を表示しない
        if (session.getAttribute("isLogin") == null) {
            whereStr = "WHERE wip = 0 ";
        }
        // 降順(最新から)で取得
        String queryStr = "SELECT * FROM articles " + whereStr + "ORDER BY id DESC";
        List<Map<String, Object>> articleList = jdbcT.queryForList(queryStr);
        mav.addObject("articlesList", articleList);
        mav.addObject("errStr", "");
        mav.setViewName("contents/articles");
        return mav;
    }
}
