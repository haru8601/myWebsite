package com.haroot.home_page.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_likes")
@Data
@NoArgsConstructor
public class ArticleLikeEntity {
  @Id
  private int articleId;

  private int likeCount;

  @UpdateTimestamp
  private LocalDateTime lastUpdate;

  public ArticleLikeEntity(int articleId, int likeCount) {
    this.articleId = articleId;
    this.likeCount = likeCount;
  }

}
