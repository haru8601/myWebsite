package com.haroot.home_page.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.ArticleDto;
import com.haroot.home_page.entity.ArticleEntity;
import com.haroot.home_page.exception.HarootNotFoundException;
import com.haroot.home_page.repository.ArticleRepository;

import jakarta.servlet.http.HttpSession;
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

	private final ArticleRepository articleRepository;
	private final HttpSession session;

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
		return ArticleDto
				.of(articleRepository.findById(idNum).orElseThrow(() -> new HarootNotFoundException("記事が見つかりませんでした")));
	}

	/**
	 * 記事取得と修正(1件)
	 * 
	 * @param id 記事ID
	 * @return
	 */
	public ArticleDto getAndFixArticle(String id) {
		ArticleDto article = getArticle(id);
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
	public List<ArticleDto> getAllArticle() {
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
	public ArticleEntity updateArticle(ArticleDto article) {
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
