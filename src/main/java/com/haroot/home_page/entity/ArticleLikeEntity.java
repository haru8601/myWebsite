package com.haroot.home_page.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_like")
@Data
@NoArgsConstructor
public class ArticleLikeEntity {
	@Id
	private int id;

	private int likeCount;

	@UpdateTimestamp
	private LocalDateTime lastUpdate;

	public ArticleLikeEntity(int id, int likeCount) {
		this.id = id;
		this.likeCount = likeCount;
	}

}
