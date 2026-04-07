package com.haroot.home_page.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleDetailDto {
  private int id;

  @NotBlank
  private String title;
  @NotBlank
  private String content;
  private boolean wip;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
  private int likeCount;
  private List<ArticleTagDto> tagList;

  public ArticleDetailDto(
      int id,
      String title,
      String content,
      boolean wip,
      LocalDateTime createDate,
      LocalDateTime updateDate,
      int likeCount,
      List<ArticleTagDto> tagList) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.wip = wip;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.likeCount = likeCount;
    this.tagList = tagList;
  }
}
