package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * youtubeプロパティ
 * @author sekiharuhito
 *
 */
@ConfigurationProperties(prefix = "youtube")
@Data
public class YoutubeProperties {
	private String key;
	private String playlistId;
}
