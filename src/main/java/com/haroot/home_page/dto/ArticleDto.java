package com.haroot.home_page.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.haroot.home_page.entity.ArticleEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 記事データ
 * 
 * @author haroot
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto implements Serializable {
  private static final long serialVersionUID = 548863522455651433L;
  private int id;

  @NotBlank
  private String title;
  @NotBlank
  private String content;
  private boolean wip;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  public ArticleDto(int id, String title, String content, boolean wip) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.wip = wip;
  }

  public static ArticleDto of(ArticleEntity entity) {
    return new ArticleDto(
      entity.getId(),
      entity.getTitle(),
      entity.getContent(),
      entity.isWip(),
      entity.getCreateDate(),
      entity.getUpdateDate());
  }

  public static List<ArticleDto> listOf(List<ArticleEntity> entityList) {
    List<ArticleDto> dtoList = new ArrayList<>();
    entityList.forEach((entity) -> dtoList.add(of(entity)));
    return dtoList;
  }
}
