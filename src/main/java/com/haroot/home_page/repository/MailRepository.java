package com.haroot.home_page.repository;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MailRepository {
  private final MailSender sender;

  private static final String FROM_ADDRESS = "noreply@haroot.net";
  private static final String TO_ADDRESS = "haroot.net@gmail.com";

  /**
   * メール送信
   *
   * @param subject 件名
   * @param message 本文
   * @return true: 送信成功, false: 送信失敗
   */
  public void sendMail(String subject, String message) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom(FROM_ADDRESS);
    msg.setTo(TO_ADDRESS);
    msg.setSubject(subject);
    msg.setText(message);
    sender.send(msg);
  }
}
