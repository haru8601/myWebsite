package com.haroot.home_page.model;

import javax.validation.constraints.NotBlank;

public class ArticleData {
	@NotBlank
	private String title;
	@NotBlank
	private String content;

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
}
