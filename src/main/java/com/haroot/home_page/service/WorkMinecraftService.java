package com.haroot.home_page.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.VideoDto;
import com.haroot.home_page.repository.VideoRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author haroot
 */
@Service
@RequiredArgsConstructor
public class WorkMinecraftService {
  private final VideoRepository videoRepository;

  private static final int VIDEO_SIZE_MAX = 50;

  public List<VideoDto> findAllWithoutShort() {
    return videoRepository.findAll(VIDEO_SIZE_MAX)
        .stream()
        // NOTE: ショートを除外するのに適したエンドポイントがないため
        // タイトルで絞り込みを行う
        .filter(video -> video
            .getTitle()
            .indexOf("#Shorts") == -1)
        .toList();
  }
}
