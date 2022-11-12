package com.haroot.home_page.model;

import javax.validation.constraints.NotBlank;

/**
 * 記事データ
 * @author sekiharuhito
 *
 */
public class ArticleData {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private boolean isPrivate;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean getIsPrivate() {
		return isPrivate;
	}
}
