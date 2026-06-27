package com.haroot.home_page.controller;

import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.FormDto;
import com.haroot.home_page.exception.HarootFraudeException;
import com.haroot.home_page.properties.GoogleProperty;
import com.haroot.home_page.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 問い合わせコントローラー
 *
 * @author haroot
 *
 */
@Controller
@RequestMapping("contact")
@RequiredArgsConstructor
@Slf4j
public class ContactController {

  private final ContactService contactService;
  private final GoogleProperty googleProperty;

  /**
   * 問い合わせ画面表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView contactLink(ModelAndView mav) {
    FormDto formDto = new FormDto();
    mav.addObject("formDto", formDto);
    mav.addObject("recaptchaSiteKey", googleProperty.getRecaptcha().getSiteKey());
    mav.setViewName("contents/contact/index");
    return mav;
  }

  /**
   * 問い合わせ送信
   *
   * @param formData      問い合わせフォーム
   * @param bindingResult エラー結果
   * @param request       リクエスト
   * @param mav           MAV
   * @return
   */
  @PostMapping("send")
  public ModelAndView postContact(@ModelAttribute @Validated FormDto formDto, BindingResult bindingResult,
      ModelAndView mav) {
    if (!isValidContent(formDto.getContent())) {
      FieldError fieldError = new FieldError(
          "formData",
          "content",
          "スパムっぽいので拒否します。");
      bindingResult.addError(fieldError);
    }

    // エラーがあれば戻る
    if (bindingResult.hasErrors()) {
      mav.addObject("formDto", formDto);
      mav.addObject("recaptchaSiteKey", googleProperty.getRecaptcha().getSiteKey());
      mav.setViewName("contents/contact/index");
      return mav;
    }

    try {
      contactService.register(formDto);
    } catch (HarootFraudeException e) {
      log.warn("お問い合わせ送信時に不正検知", e);
      mav.addObject("errStr", "お問い合わせの送信に失敗しました。時間をおいて再度お試しください。");
      mav.addObject("formDto", formDto);
      mav.addObject("recaptchaSiteKey", googleProperty.getRecaptcha().getSiteKey());
      mav.setViewName("contents/contact/index");
      return mav;
    } catch (Exception e) {
      log.error("お問い合わせ送信時に予期せぬエラー: {}", e);
      mav.addObject("errStr", "お問い合わせの送信に失敗しました。時間をおいて再度お試しください。");
      mav.addObject("formDto", formDto);
      mav.addObject("recaptchaSiteKey", googleProperty.getRecaptcha().getSiteKey());
      mav.setViewName("contents/contact/index");
      return mav;
    }

    mav.setViewName("contents/contact/sent");
    return mav;
  }

  /**
   * true: 問い合わせ内容が適切 false: 不適切
   */
  private boolean isValidContent(String content) {
    Pattern mailAddressPattern = Pattern.compile("[a-zA-Z0-9]+@([a-zA-Z0-9]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}");
    Pattern urlPattern = Pattern.compile("https?://.*\\.");
    // その他のエラー
    Pattern invalidPattern = Pattern.compile("@Cryptaxbot");

    // お問い合わせ内容にメールアドレスやリンクが含まれていたらエラーを追加
    if (mailAddressPattern.matcher(content).find() ||
        urlPattern.matcher(content).find() ||
        invalidPattern.matcher(content).find()) {
      return false;
    }
    return true;
  }
}
