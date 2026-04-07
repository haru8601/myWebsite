package com.haroot.home_page.quereyService;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.ArticleDetailDto;
import com.haroot.home_page.dto.ArticleDto;
import com.haroot.home_page.dto.ArticleLikeDto;
import com.haroot.home_page.dto.ArticleTagDto;
import com.haroot.home_page.entity.ArticleEntity;
import com.haroot.home_page.repository.ArticleLikeRepository;
import com.haroot.home_page.repository.ArticleRepository;
import com.haroot.home_page.repository.ArticleTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleQueryService {
    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleTagRepository articleTagRepository;

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
    private List<ArticleDetailDto> findAllWithCustomArticleGetter(Supplier<List<ArticleEntity>> getArticleFunction) {
        // 公開記事一覧を取得
        List<ArticleDto> articleList = ArticleDto.listOf(getArticleFunction.get());

        // 全記事のいいね数取得
        List<ArticleLikeDto> articleLikeList = ArticleLikeDto.listOf(articleLikeRepository.findAll());

        // 全記事のタグ取得
        List<ArticleTagDto> articleTagList = ArticleTagDto.listOf(articleTagRepository.findAll());

        // いいね数一覧を記事IDごとのマップに変換
        Map<Integer, Integer> articleLikeMap = articleLikeList
                .stream()
                .collect(Collectors.toMap(ArticleLikeDto::getArticleId, val -> val.getLikeCount()));

        // タグ一覧を記事IDごとのマップに変換
        Map<Integer, List<ArticleTagDto>> articleTagMap = articleTagList
                .stream()
                .collect(Collectors.groupingBy(ArticleTagDto::getArticleId));

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
