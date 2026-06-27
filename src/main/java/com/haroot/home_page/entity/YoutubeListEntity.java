package com.haroot.home_page.entity;

import java.util.List;

import lombok.Data;

@Data
public class YoutubeListEntity {
  private List<YoutubeItem> items;

  @Data
  public static class YoutubeItem {
    private YoutubeSnippet snippet;
  }

  @Data
  public static class YoutubeSnippet {
    private String title;
    private YoutubeResourceId resourceId;
  }

  @Data
  public static class YoutubeResourceId {
    private String videoId;
  }
}
