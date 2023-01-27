package com.haroot.home_page.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.PokeDto;
import com.haroot.home_page.service.PokeService;

import lombok.RequiredArgsConstructor;

/**
 * pokeコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/poke")
@RequiredArgsConstructor
public class PokeController {
  private final PokeService pokeService;

  /**
   * poke一覧画面
   * 
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView poke(ModelAndView mav) {
    List<PokeDto> pokeList = pokeService.getAll();
    for (PokeDto content : pokeList) {
      String url = content.getUrl();
      content.setUrl(url != null ? "poke/" + url : "#");
    }
    mav.addObject("pokeList", pokeList);
    mav.setViewName("contents/poke");
    return mav;
  }

  /**
   * bot表示
   * 
   * @param mav MAV
   * @return
   */
  @GetMapping("/bot")
  public ModelAndView bot(ModelAndView mav) {
    mav.setViewName("contents/poke/bot");
    return mav;
  }

  /**
   * しりとりサイト表示
   * 
   * @param mav MAV
   * @return
   */
  @GetMapping("/shiritori")
  public ModelAndView shiritori(ModelAndView mav) {
    mav.setViewName("contents/poke/shiritori");
    return mav;
  }
}
