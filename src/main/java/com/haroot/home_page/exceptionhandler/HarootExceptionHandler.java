package com.haroot.home_page.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.haroot.home_page.exception.HarootNotFoundException;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class HarootExceptionHandler {
  @ExceptionHandler(HarootNotFoundException.class)
  public String handleHarootNotFoundException(HarootNotFoundException ex, HttpServletResponse response) {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    return "/error/404";
  }
}
