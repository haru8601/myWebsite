package com.haroot.home_page.controller;

import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haroot.home_page.dto.UserDto;
import com.haroot.home_page.properties.UserProperty;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
  private final UserProperty userProperty;
  private final HttpSession session;

  @GetMapping
  public String top(@ModelAttribute UserDto userDto) {
    return "/contents/admin/index";
  }

  @PostMapping("login")
  public String login(@Validated @ModelAttribute UserDto userDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/contents/admin/index";
    }
    // ログイン成功
    if (userDto.getUsername().equals(userProperty.getUsername())
        && Base64.getEncoder().encodeToString(userDto.getPassword().getBytes()).equals(userProperty.getPassword())) {
      session.setAttribute("isLogin", true);
      return "/contents/admin/user";
    }
    bindingResult.rejectValue("username", null, "ユーザー名またはパスワードが違います");
    return "/contents/admin/index";
  }
}
