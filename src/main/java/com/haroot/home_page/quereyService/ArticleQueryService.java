package com.haroot.home_page.quereyService;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.ArticleDetailDto;
import com.haroot.home_page.dto.ArticleDto;
import com.haroot.home_page.dto.ArticleLikeDto;
import com.haroot.home_page.dto.ArticleTagDetailDto;
import com.haroot.home_page.entity.ArticleEntity;
import com.haroot.home_page.entity.ArticleTagEntity;
import com.haroot.home_page.entity.TagEntity;
import com.haroot.home_page.repository.ArticleLikeRepository;
import com.haroot.home_page.repository.ArticleRepository;
import com.haroot.home_page.repository.ArticleTagRepository;
import com.haroot.home_page.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleQueryService {
  private final ArticleRepository articleRepository;
  private final ArticleLikeRepository articleLikeRepository;
  private final ArticleTagRepository articleTagRepository;
  private final TagRepository tagRepository;

  /**
   * 非公開記事を除く全記事取得
   */
  public List<ArticleDetailDto> findAllWithoutPrivate() {
    return findAllWithCustomArticleGetter(
        articleRepository::findAllByWipFalseOrderByUpdateDateDesc);
  }

  /**
   * 非公開記事を含む全記事取得
   *
   * @return
   */
  public List<ArticleDetailDto> findAll() {
    return findAllWithCustomArticleGetter(
        articleRepository::findAllByOrderByUpdateDateDesc);
  }

  /**
   * 指定の記事一覧取得関数を実行後、いいねやタグを取得して返却
   *
   * @param getArticleFunction
   * @return
   */
  private List<ArticleDetailDto> findAllWithCustomArticleGetter(
      Supplier<List<ArticleEntity>> getArticleFunction) {
    // 公開記事一覧を取得
    List<ArticleDto> articleList = ArticleDto.listOf(getArticleFunction.get());

    // 全記事のいいね数取得
    List<ArticleLikeDto> articleLikeList = ArticleLikeDto.listOf(articleLikeRepository.findAll());

    // 全記事のタグ取得
    List<ArticleTagEntity> articleTagList = articleTagRepository.findAll();
    // タグ一覧の取得
    List<TagEntity> tagList = tagRepository.findAll();
    // 全記事のタグにタグ名を紐付け
    List<ArticleTagDetailDto> articleTagDetailList = ArticleTagDetailDto.listOf(articleTagList, tagList);

    // いいね数一覧を記事IDごとのマップに変換
    Map<Integer, Integer> articleLikeMap = articleLikeList
        .stream()
        .collect(Collectors.toMap(ArticleLikeDto::getArticleId, val -> val.getLikeCount()));

    // タグ一覧を記事IDごとのマップに変換
    Map<Integer, List<ArticleTagDetailDto>> articleTagMap = articleTagDetailList
        .stream()
        .collect(Collectors.groupingBy(ArticleTagDetailDto::getArticleId));

    return articleList
        .stream()
        .map((article) -> new ArticleDetailDto(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.isWip(),
            article.getCreateDate(),
            article.getUpdateDate(),
            articleLikeMap.getOrDefault(
                article.getId(), 0),
            articleTagMap.getOrDefault(
                article.getId(), List.of())))
        .toList();
  }
}
