package com.haroot.home_page.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.haroot.home_page.controller.entity.ContactEntity;
import com.haroot.home_page.dto.FormDto;
import com.haroot.home_page.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {
    final HttpServletRequest request;
    final ContactRepository contactRepository;

    public ContactEntity register(FormDto formDto) {
        String name = formDto.getName();
        String email = formDto.getEmail();
        String content = formDto.getContent();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        ContactEntity contactEntity = new ContactEntity(name, email, content, ip);
        // DBに保存
        return contactRepository.save(contactEntity);
    }
}
