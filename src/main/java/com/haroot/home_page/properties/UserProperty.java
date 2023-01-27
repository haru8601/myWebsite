package com.haroot.home_page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "user")
@Data
public class UserProperty {
  private String username;
  private String password;
}
