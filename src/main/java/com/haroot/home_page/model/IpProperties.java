package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * IPプロパティ
 * @author sekiharuhito
 *
 */
@ConfigurationProperties(prefix = "ip-info")
public class IpProperties {
	private String ipAddress;
	public void setIpAddress(String ip) {
		this.ipAddress = ip;
	}
	public String getIpAddress() {
		return ipAddress;
	}
}
