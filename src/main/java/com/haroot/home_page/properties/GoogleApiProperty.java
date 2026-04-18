package com.haroot.home_page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * youtubeプロパティ
 *
 * @author haroot
 *
 */
@ConfigurationProperties(prefix = "google-api")
@Data
public class GoogleApiProperty {
  private String baseUrl;
  private Youtube youtube;

  @Data
  public static class Youtube {
    private String key;
    private String playlistId;
    private String baseUrl;
    private String imageUrl;
  }
}
