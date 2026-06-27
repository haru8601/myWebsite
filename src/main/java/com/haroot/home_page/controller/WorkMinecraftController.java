package com.haroot.home_page.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.VideoDto;
import com.haroot.home_page.dto.WorkDetailDto;
import com.haroot.home_page.exception.HarootServerException;
import com.haroot.home_page.service.WorkMinecraftService;
import com.haroot.home_page.service.WorkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("work/music/minecraft-music")
@RequiredArgsConstructor
@Slf4j
public class WorkMinecraftController {
  private final WorkMinecraftService service;
  private final WorkService workService;
  private static final String FIRST_VIEW_VIDEO_ID = "R6V18nhDUPk";

  /**
   * minecraft動画一覧表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView index(ModelAndView mav) {
    try {
      WorkDetailDto work = workService.getWithTags("minecraft-music");

      List<VideoDto> videoList = service.findAllWithoutShort();

      VideoDto firstViewVideo = videoList
          .stream()
          .filter(video -> video.getVideoId().equals(FIRST_VIEW_VIDEO_ID))
          .findFirst()
          .orElse(null);
      firstViewVideo.convertToHdImage();

      mav.addObject("work", work);
      mav.addObject("videoList", videoList);
      mav.addObject("firstViewVideo", firstViewVideo);
      mav.setViewName("contents/work/music/minecraft-music");
      return mav;
    } catch (Throwable e) {
      System.err.println("マイクラ音楽の取得に失敗しました.");
      System.err.println(e.getMessage());
      e.printStackTrace();
      throw new HarootServerException("マイクラ音楽の取得に失敗しました.", e);
    }
  }
}
