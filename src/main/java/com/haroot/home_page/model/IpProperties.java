package com.haroot.home_page.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

// TODO: 削除してAuthによる認証に変更
/**
 * IPプロパティ
 * 
 * @author sekiharuhito
 *
 */
@ConfigurationProperties(prefix = "ip-info")
@Data
public class IpProperties {
    private String ipAddress;
}
