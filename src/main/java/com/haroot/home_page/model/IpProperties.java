package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * IPプロパティ
 * @author sekiharuhito
 *
 */
@ConfigurationProperties(prefix = "ip-info")
@Data
public class IpProperties {
	private String ipAddress;
}
