package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "path-info")
public class PathProperties {
	private String homepath;

	public void setHomepath(String homepath) {
		this.homepath = homepath;
	}

	public String getHomepath() {
		return homepath;
	}
}
