package com.haroot.home_page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * youtubeプロパティ
 *
 * @author haroot
 *
 */
@ConfigurationProperties(prefix = "youtube")
@Data
public class YoutubeProperty {
  private String key;
  private String playlistId;
}
