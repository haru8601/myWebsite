package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Qiitaプロパティ
 * @author sekiharuhito
 *
 */
@ConfigurationProperties(prefix = "qiita")
public class QiitaProperties {
	private String token;
	private String user;

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}
}
