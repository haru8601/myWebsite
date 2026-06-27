package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.YoutubeListEntity;
import com.haroot.home_page.entity.YoutubeListEntity.YoutubeSnippet;
import com.haroot.home_page.properties.GoogleApiProperty;
import com.haroot.home_page.properties.GoogleApiProperty.Youtube;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author haroot
 */
@Data
@AllArgsConstructor
public class VideoDto {
  private String videoId;
  private String title;
  private String url;
  private String imagePath;

  public static List<VideoDto> listOf(
      YoutubeListEntity entityList,
      GoogleApiProperty googleApiProperty) {
    return entityList
        .getItems()
        .stream()
        .map(entity -> {
          YoutubeSnippet snippet = entity.getSnippet();
          Youtube youtubeProperty = googleApiProperty.getYoutube();
          String videoId = snippet.getResourceId().getVideoId();

          return new VideoDto(
              snippet.getResourceId().getVideoId(),
              snippet.getTitle(),
              // FIXME: Youtubeのエンドポイントのパスに依存している
              youtubeProperty.getBaseUrl() + "/watch?v=" + videoId,
              youtubeProperty.getImageUrl() + "/vi/" + videoId + "/mqdefault.jpg");
        })
        .toList();
  }

  /**
   * 高画質urlへの変換
   */
  public void convertToHdImage() {
    this.imagePath = imagePath.replaceAll("/[a-z]+\\.jpg", "/maxresdefault.jpg");
  }
}
