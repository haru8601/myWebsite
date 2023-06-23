package com.haroot.home_page.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HomePageFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestUri = httpRequest.getRequestURI();
    // ルート("/")以外で末尾にスラッシュがある場合
    if (requestUri.matches("..*\\/$")) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      String redirectUri = requestUri.replaceAll("(..*)\\/$", "$1");
      String queryStr = httpRequest.getQueryString();
      if (queryStr != null) {
        redirectUri += "?" + queryStr;
      }
      // 末尾のスラッシュを取り除いてリダイレクト
      httpResponse.sendRedirect(redirectUri);
      return;
    }
    // 後続処理へ
    chain.doFilter(request, response);
  }
}
