package com.haroot.home_page.dto;

import java.time.LocalDateTime;

import com.haroot.home_page.entity.ArticleLikeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleLikeDto {
	private int id;
	private int likeCount;
	private LocalDateTime lastUpdate;

	public static ArticleLikeDto of(ArticleLikeEntity entity) {
		return new ArticleLikeDto(entity.getId(), entity.getLikeCount(), entity.getLastUpdate());
	}
}
