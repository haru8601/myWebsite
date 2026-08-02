package com.haroot.home_page.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.haroot.home_page.exception.HarootNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class HarootExceptionHandler {
  @ExceptionHandler(HarootNotFoundException.class)
  public String handleHarootNotFoundException(
      HarootNotFoundException ex, HttpServletRequest request, HttpServletResponse response, Model model) {
    // 例外発生時はWebInterceptor#postHandleが呼ばれず"uri"がモデルに追加されないため、ここで補う
    model.addAttribute("uri", request.getRequestURI());
    response.setStatus(HttpStatus.NOT_FOUND.value());
    return "/error/404";
  }
}
