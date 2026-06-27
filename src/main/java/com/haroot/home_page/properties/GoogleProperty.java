package com.haroot.home_page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * googleプロパティ
 *
 * @author haroot
 *
 */
@ConfigurationProperties(prefix = "google")
@Data
public class GoogleProperty {
  private String baseUrl;
  private Recaptcha recaptcha;

  @Data
  public static class Recaptcha {
    private String siteKey;
    private String secret;
    private float threshold;
  }
}
