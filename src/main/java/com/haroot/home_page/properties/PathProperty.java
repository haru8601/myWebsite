package com.haroot.home_page.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "path")
@Data
public class PathProperty {
    private String log;
    private String site;
    private String resources;
}
