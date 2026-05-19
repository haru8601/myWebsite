package com.haroot.home_page.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.haroot.home_page.entity.ArticleLikeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleLikeDto {
  private int articleId;
  private int likeCount;
  private LocalDateTime lastUpdate;

  public static ArticleLikeDto of(ArticleLikeEntity entity) {
    return new ArticleLikeDto(
        entity.getArticleId(),
        entity.getLikeCount(),
        entity.getLastUpdate());
  }

  public static List<ArticleLikeDto> listOf(List<ArticleLikeEntity> entityList) {
    return entityList
        .stream()
        .map(entity -> of(entity))
        .toList();
  }
}
