package com.haroot.home_page.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_tags")
@Data
@NoArgsConstructor
public class ArticleTagEntity {
  @Id
  private int id;

  private int articleId;

  private int tagId;

  public ArticleTagEntity(int id, int articleId, int tagId) {
    this.id = id;
    this.articleId = articleId;
    this.tagId = tagId;
  }
}
