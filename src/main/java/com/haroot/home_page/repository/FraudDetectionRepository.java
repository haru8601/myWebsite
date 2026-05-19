package com.haroot.home_page.repository;

import org.springframework.stereotype.Repository;

import com.haroot.home_page.client.GoogleClient;
import com.haroot.home_page.entity.RecaptchaVerifyEntity;
import com.haroot.home_page.properties.GoogleProperty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionRepository {
  private final GoogleClient googleClient;
  private final GoogleProperty googleProperty;

  /**
   * 不正アクセスかどうかの判定
   *
   * @param recaptchaResponse
   * @param remoteIp
   * @return true: 不正アクセス, false: 正常アクセス
   */
  public boolean isFraud(String recaptchaResponse, String remoteIp) {
    RecaptchaVerifyEntity response = googleClient.verifyRecaptcha(
        googleProperty.getRecaptcha().getSecret(),
        recaptchaResponse,
        remoteIp);
    if (response.isSuccess()) {
      return false;
    }
    log.debug("score: {}, challenge_ts: {}, response: {}", response.getScore(), response.getChallenge_ts(), response);
    if (response.getScore() < googleProperty.getRecaptcha().getThreshold()) {
      log.warn("Potential fraud detected. IP: {}, Score: {}", remoteIp, response.getScore());
      return true;
    }
    return false;
  }
}
