package com.haroot.home_page.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.springframework.stereotype.Component;

import com.haroot.home_page.dto.ArticleDto;
import com.haroot.home_page.properties.MemcachedProperty;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;

@Component
@Slf4j
public class Memcached {
  private MemcachedClient client;
  private MemcachedProperty property;

  public Memcached(MemcachedProperty property) {
    this.property = property;
    try {
      this.client = new MemcachedClient(
        new InetSocketAddress(
          property.getHost(),
          property.getPort()));
    } catch (IOException e) {
      log.error("memcached接続エラー");
      log.error(e.getMessage());
    }
  }

  public void setArticle(String key, ArticleDto o) {
    client.set(key, property.getExpSec(), o);
  }

  public ArticleDto getArticle(String key) {
    try {
      Object o = client.get(key);
      if (o == null) {
        return null;
      }
      return (ArticleDto) o;
    } catch (ClassCastException e) {
      log.error("記事キャストエラー");
      log.error(e.getMessage());
      return null;
    } catch (Exception e) {
      log.error("記事キャッシュ取得エラー");
      log.error(e.getMessage());
      return null;
    }
  }
}
