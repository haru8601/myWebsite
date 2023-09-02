package com.haroot.home_page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Qiitaプロパティ
 *
 * @author haroot
 *
 */
@ConfigurationProperties(prefix = "qiita")
@Data
public class QiitaProperty {
  private String token;
  private String user;
}
