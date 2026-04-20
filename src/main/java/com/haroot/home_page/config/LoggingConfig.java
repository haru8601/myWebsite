package com.haroot.home_page.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class LoggingConfig {
  // リクエストログの設定
  @Bean
  CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
      @Override
      protected boolean shouldLog(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !uri.startsWith("/images") &&
            !uri.startsWith("/dist") &&
            !uri.startsWith("/.well-known") &&
            !uri.startsWith("/js");
      }

      @Override
      protected void afterRequest(HttpServletRequest request, String message) {
        // After requestは出力しない
      }
    };
    filter.setIncludeQueryString(true);
    return filter;
  }

}
