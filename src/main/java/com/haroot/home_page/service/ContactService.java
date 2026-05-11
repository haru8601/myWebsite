package com.haroot.home_page.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.FormDto;
import com.haroot.home_page.entity.ContactEntity;
import com.haroot.home_page.repository.ContactRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {
  private final HttpServletRequest request;
  private final ContactRepository contactRepository;

  public ContactEntity register(FormDto formDto) {
    String name = formDto.getName();
    String email = formDto.getEmail();
    String content = formDto.getContent();
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null) {
      ip = request.getRemoteAddr();
    }
    // 特定IPのブロック
    List<String> blockIpList = List.of("178.128.102.105");
    if (blockIpList.contains(ip)) {
      System.err.println("Blocked IP. IP: " + ip);
      throw new IllegalArgumentException("お問い合わせの送信に失敗しました。");
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
