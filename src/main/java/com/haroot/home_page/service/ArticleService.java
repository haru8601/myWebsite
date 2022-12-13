package com.haroot.home_page.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.controller.entity.ArticleEntity;
import com.haroot.home_page.dto.ArticleDto;
import com.haroot.home_page.exception.HarootNotFoundException;
import com.haroot.home_page.properties.QiitaProperty;
import com.haroot.home_page.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

/**
 * MAVクラス
 * 
 * @author sekiharuhito
 *
 */
@Service
@RequiredArgsConstructor
public class ArticleService {

    final ArticleRepository articleRepository;
    final HttpSession session;
    final QiitaProperty qiitaProperty;

    /**
     * 記事取得(1件)
     * 
     * @param id 記事ID
     * @return
     */
    public ArticleDto getArticle(String id) {
        int idNum = -1;
        try {
            idNum = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new HarootNotFoundException(ex.getMessage(), ex);
        }
        ArticleEntity article = articleRepository.findById(idNum)
                .orElseThrow(() -> new HarootNotFoundException("記事が見つかりませんでした"));
        if (article.isWip() && session.getAttribute("isLogin") == null) {
            // 非公開なら情報マスキング
            article = maskArticle(article);
        } else {
            int tmpLikeCount = article.getLikeCount();
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
                    if (qiitaTitle.equals(article.getTitle())) {
                        tmpLikeCount += node.get("likes_count").asInt();
                        break;
                    }
                }
                article.setLikeCount(tmpLikeCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ArticleDto.of(article);
    }

    /**
     * 記事取得(複数)
     * 
     * @return
     */
    public List<ArticleEntity> getAllArticle() {
        List<ArticleEntity> articleList = new ArrayList<>();
        // ログインユーザーでなければ公開記事のみ表示
        if (session.getAttribute("isLogin") == null) {
            articleList = articleRepository.findAllByWipFalseOrderByUpdateDateDesc();
        } else {
            articleList = articleRepository.findAllByOrderByUpdateDateDesc();
        }
        return articleList;
    }

    /**
     * 記事更新
     * 
     * @param article 記事
     * @return 更新後の記事
     */
    public ArticleEntity updateArticle(ArticleDto article) {
        return articleRepository.save(ArticleEntity.of(article));
    }

    /**
     * 記事の情報を隠す
     * 
     * @param article 記事
     * @return マスキング後の記事
     */
    public static ArticleEntity maskArticle(ArticleEntity article) {
        article.setTitle("この記事は非公開です");
        article.setContent("");
        article.setLikeCount(0);
        article.setCreateDate(null);
        article.setUpdateDate(null);
        return article;
    }
}
