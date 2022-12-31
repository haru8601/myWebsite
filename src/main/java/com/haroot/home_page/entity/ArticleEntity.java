package com.haroot.home_page.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.haroot.home_page.dto.ArticleDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "articles")
@Data
public class ArticleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	private String content;

	private int likeCount;

	private boolean wip;

	@CreationTimestamp
	private LocalDateTime createDate;

	@UpdateTimestamp
	private LocalDateTime updateDate;

	public static ArticleEntity of(ArticleDto dto) {
		ArticleEntity entity = new ArticleEntity();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		entity.setWip(dto.isWip());
		entity.setCreateDate(dto.getCreateDate());
		return entity;
	}
}
