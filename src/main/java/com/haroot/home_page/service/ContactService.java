package com.haroot.home_page.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.FormDto;
import com.haroot.home_page.entity.ContactEntity;
import com.haroot.home_page.exception.HarootFraudeException;
import com.haroot.home_page.repository.ContactRepository;
import com.haroot.home_page.repository.FraudDetectionRepository;
import com.haroot.home_page.repository.MailRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haroot
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {
  private final HttpServletRequest request;
  private final ContactRepository contactRepository;
  private final MailRepository mailRepository;
  private final FraudDetectionRepository fraudDetectionRepository;

  /**
   * お問い合わせ登録
   *
   * @param formDto フォーム
   * @return お問い合わせ情報
   */
  public ContactEntity register(FormDto formDto) {
    String name = formDto.getName();
    String email = formDto.getEmail();
    String content = formDto.getContent();
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null) {
      ip = request.getRemoteAddr();
    }

    // 不正アクセスの判定
    if (fraudDetectionRepository.isFraud(formDto.getRecaptchaToken(), ip)) {
      log.warn("不正アクセスと判断されました。IP: {}, Token: {}", ip, formDto.getRecaptchaToken());
      throw new HarootFraudeException("不正アクセスと判断されました。");
    }

    // 特定IPのブロック
    List<String> blockIpList = List.of("178.128.102.105");
    if (blockIpList.contains(ip)) {
      log.warn("ブロックされたIPです。IP: {}", ip);
      throw new IllegalArgumentException("お問い合わせの送信に失敗しました。");
    }

    // メール送信
    String subject = "【通知】お問い合わせがありました";
    String br = System.getProperty("line.separator");
    String message = formDto.getName() + "さんからお問い合わせがありました。" + br + br + "メールアドレス: " + formDto.getEmail() + br + br
        + "お問い合わせ内容: " + br + formDto.getContent();
    try {
      mailRepository.sendMail(subject, message);
    } catch (Exception e) {
      log.error("メールの送信に失敗しました。", e);
      throw new RuntimeException("メールの送信に失敗しました。");
    }

    ContactEntity contactEntity = new ContactEntity(
        name,
        email,
        content,
        ip);
    // DBに保存
    return contactRepository.save(contactEntity);
  }
}
