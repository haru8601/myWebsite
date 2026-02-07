package com.haroot.home_page.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.dto.WordsDto;
import com.haroot.home_page.properties.PathProperty;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * hxhコントローラー
 *
 * @author haroot
 *
 */
@Controller
@RequestMapping("others/hxh-char-quiz")
@RequiredArgsConstructor
@Slf4j
public class HunterController {
  private final HttpSession session;

  private final PathProperty pathProperty;

  /**
   * hxh文字クイズ表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView others(ModelAndView mav) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File fishFile = new File(pathProperty.getStaticResources() + "/json/words/fish.json");
      File vegetablesFile = new File(pathProperty.getStaticResources() + "/json/words/vegetables.json");
      if (!fishFile.exists() || !vegetablesFile.exists()) {
        System.out.println("fish or vegetables file not found.");
        System.out.println(fishFile.getAbsolutePath());
        System.out.println(vegetablesFile.getAbsolutePath());
      }
      BufferedReader fishBr = Files.newBufferedReader(fishFile.toPath(), Charset.forName("UTF-8"));
      WordsDto fishJson = mapper.readValue(fishBr, WordsDto.class);
      BufferedReader vegetablesBr = Files.newBufferedReader(vegetablesFile.toPath(), Charset.forName("UTF-8"));
      WordsDto vegetablesJson = mapper.readValue(vegetablesBr, WordsDto.class);
      mav.addObject("words",
          Stream.concat(fishJson.getNames().stream(), vegetablesJson.getNames().stream()).collect(Collectors.toList()));
    } catch (IOException e) {
      System.out.println("Failed to load words.");
      System.out.println(e.getMessage());
    }

    mav.setViewName("contents/others/hxh-char-quiz");
    return mav;
  }
}
