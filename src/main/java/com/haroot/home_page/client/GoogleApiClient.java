package com.haroot.home_page.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.haroot.home_page.entity.YoutubeListEntity;

@HttpExchange("/youtube/v3/playlistItems")
public interface GoogleApiClient {
  @GetExchange
  YoutubeListEntity getPlaylistItems(
      @RequestParam String part,
      @RequestParam String playlistId,
      @RequestParam String key,
      @RequestParam int maxResults);
}
