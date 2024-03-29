package com.haroot.home_page.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.ArticleDto;
import com.haroot.home_page.entity.ArticleEntity;
import com.haroot.home_page.exception.HarootNotFoundException;
import com.haroot.home_page.repository.ArticleRepository;
import com.haroot.home_page.repository.RedisRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MAVクラス
 *
 * @author haroot
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final HttpSession session;
  private final RedisRepository redisRepository;
  private final String prefix = "articles-";

  /**
   * 記事取得(1件)
   *
   * @param id 記事ID
   * @return
   */
  public ArticleDto getOne(String id) {
    int idNum = -1;
    try {
      idNum = Integer.parseInt(id);
    } catch (NumberFormatException ex) {
      throw new HarootNotFoundException(
        ex.getMessage(),
        ex);
    }
    ArticleDto article = null;
    try {
      article = (ArticleDto) redisRepository.get(prefix + id);
    } catch (Exception e) {
      log.error("redis get error");
      log.error(e.getMessage());
    }
    if (article != null) {
      log.debug("cache hit, id:" + id);
      return article;
    }
    log.debug("cache not found, id:" + id);
    article = ArticleDto.of(articleRepository.findById(idNum).orElseThrow(() -> new HarootNotFoundException(
      "記事が見つかりませんでした")));
    try {
      redisRepository.set(prefix + id, article);
    } catch (Exception e) {
      log.error("redis set error");
      log.error(e.getMessage());
    }
    return article;
  }

  /**
   * 記事取得と修正(1件)
   *
   * @param id 記事ID
   * @return
   */
  public ArticleDto getAndFixArticle(String id) {
    ArticleDto article = getOne(id);
    if (article.isWip() && session.getAttribute("isLogin") == null) {
      // 非公開なら情報マスキング
      article = maskArticle(article);
    }
    return article;

  }

  /**
   * 記事取得(複数)
   *
   * @return
   */
  public List<ArticleDto> getAll() {
    List<ArticleEntity> articleList = new ArrayList<>();
    // ログインユーザーでなければ公開記事のみ表示
    if (session.getAttribute("isLogin") == null) {
      articleList = articleRepository.findAllByWipFalseOrderByUpdateDateDesc();
    } else {
      articleList = articleRepository.findAllByOrderByUpdateDateDesc();
    }
    return ArticleDto.listOf(articleList);
  }

  /**
   * 記事更新
   *
   * @param article 記事
   * @return 更新後の記事
   */
  public ArticleEntity update(ArticleDto article) {
    if (article.getId() != -1) {
      // 既存記事ならキャッシュ更新
      try {
        redisRepository.set(prefix + article.getId(), article);
      } catch (Exception e) {
        log.error("redis set error");
        log.error(e.getMessage());
      }
    }
    return articleRepository.save(ArticleEntity.of(article));
  }

  /**
   * 記事の情報を隠す
   *
   * @param article 記事
   * @return マスキング後の記事
   */
  public static ArticleDto maskArticle(ArticleDto article) {
    article.setTitle("この記事は非公開です");
    article.setContent("");
    article.setCreateDate(null);
    article.setUpdateDate(null);
    return article;
  }
}
