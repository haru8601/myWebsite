package com.haroot.home_page.logic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.model.IpProperties;
import com.haroot.home_page.model.QiitaProperties;

public class MavUtils {
    public static ModelAndView getArticleMav(ModelAndView mav, String id, QiitaProperties qiitaProperties,
            JdbcTemplate jdbcT, HttpServletRequest request, IpProperties ipProperties) {
        Map<String, Object> article = jdbcT.queryForList("SELECT * FROM articles WHERE id = " + id).get(0);
        int isPrivate = (int) article.get("isPrivate");

        // 自分以外かつ非公開なら表示しない
        String clientIp = IpLogic.getIp(request);
        String title = "この記事は非公開です";
        int likeCount = 0;
        String content = "";

        // 表示する場合
        if (isPrivate == 0 || clientIp.equals(ipProperties.getIpAddress())) {
            title = article.get("title").toString();
            likeCount = Integer.parseInt(article.get("like_count").toString());
            content = article.get("content").toString();

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
        }

        mav.addObject("id", id);
        mav.addObject("title", title);
        mav.addObject("content", content);
        mav.addObject("like_count", likeCount);
        mav.addObject("isPrivate", isPrivate);

        mav.setViewName("contents/articles/template");
        return mav;
    }

    public static ModelAndView getArticleListMav(ModelAndView mav, JdbcTemplate jdbcT, String clientIp,
            IpProperties ipProperties) {
        String whereStr = "";
        // 自分以外のIPは非公開を表示しない
        if (!clientIp.equals(ipProperties.getIpAddress())) {
            whereStr = "WHERE isPrivate = 0 ";
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
