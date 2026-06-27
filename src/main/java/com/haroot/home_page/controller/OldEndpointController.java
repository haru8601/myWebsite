package com.haroot.home_page.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;

// 古いエンドポイントのリダイレクト用コントローラー
@Controller
@RequiredArgsConstructor
public class OldEndpointController {
  @GetMapping("/ios-app/english-quiz")
  public RedirectView englishQuiz() {
    return createRedirectView("/work/apps/english-quiz");
  }

  @GetMapping("/ios-app/english-quiz/policy")
  public RedirectView englishQuizPolicy() {
    return createRedirectView("/policy/english-quiz");
  }

  @GetMapping("/music/minecraft")
  public RedirectView minecraftMusic() {
    return createRedirectView("/work/music/minecraft-music");
  }

  @GetMapping("/poke/bot")
  public RedirectView pokeBot() {
    return createRedirectView("/work/sns/poke-bot");
  }

  @GetMapping("/poke/shiritori")
  public RedirectView pokeShiritori() {
    return createRedirectView("/work/game/poke-shiritori");
  }

  @GetMapping("/others/hxh-char-quiz")
  public RedirectView hunterScriptQuiz() {
    return createRedirectView("/work/game/hunter-script-quiz");
  }

  /**
   * 永続的にリダイレクトであることを示すRedirectViewを作成
   *
   * @param newUrl
   * @return
   */
  static RedirectView createRedirectView(String newUrl) {
    RedirectView rv = new RedirectView(newUrl);
    rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
    return rv;
  }
}
