package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
