package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Qiitaプロパティ
 * @author sekiharuhito
 *
 */
@ConfigurationProperties(prefix = "qiita")
@Data
public class QiitaProperties {
	private String token;
	private String user;
}
