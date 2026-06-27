package com.haroot.home_page.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.haroot.home_page.client.GoogleApiClient;
import com.haroot.home_page.dto.VideoDto;
import com.haroot.home_page.properties.GoogleApiProperty;
import com.haroot.home_page.properties.GoogleApiProperty.Youtube;

import lombok.RequiredArgsConstructor;

/**
 * @author haroot
 */
@Repository
@RequiredArgsConstructor
public class VideoRepository {
  private final GoogleApiClient client;
  private final GoogleApiProperty googleApiProperty;

  public List<VideoDto> findAll(int maxResults) {
    Youtube youtube = googleApiProperty.getYoutube();

    return VideoDto.listOf(
        client.getPlaylistItems(
            "snippet",
            youtube.getPlaylistId(),
            youtube.getKey(),
            maxResults),
        googleApiProperty);
  }
}
