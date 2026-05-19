package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.ArticleTagEntity;
import com.haroot.home_page.entity.TagEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleTagDetailDto {
  private int id;
  private int articleId;
  private int tagId;
  private String tagName;
  private String tagType;

  public static ArticleTagDetailDto of(ArticleTagEntity entity, List<TagEntity> tagList) {
    return tagList.stream()
        .filter(tag -> tag.getId() == entity.getTagId())
        .findFirst()
        .map(tag -> new ArticleTagDetailDto(
            entity.getId(),
            entity.getArticleId(),
            entity.getTagId(),
            tag.getName(),
            tag.getType()))
        .orElse(null);
  }

  public static List<ArticleTagDetailDto> listOf(List<ArticleTagEntity> entityList, List<TagEntity> tagList) {
    return entityList
        .stream()
        .map(entity -> of(entity, tagList))
        .toList();
  }
}
