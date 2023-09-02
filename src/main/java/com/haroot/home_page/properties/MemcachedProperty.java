package com.haroot.home_page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "memcached")
@Data
public class MemcachedProperty {
  private String host;
  private int port;
  private int expSec;
}
