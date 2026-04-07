package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.ArticleTagEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleTagDto {
  private int id;
  private int articleId;
  private int tagId;

  public static ArticleTagDto of(ArticleTagEntity entity) {
    return new ArticleTagDto(
        entity.getId(),
        entity.getArticleId(),
        entity.getTagId());
  }

  public static List<ArticleTagDto> listOf(List<ArticleTagEntity> entityList) {
    return entityList
        .stream()
        .map((entity) -> ArticleTagDto.of(entity))
        .toList();
  }
}
