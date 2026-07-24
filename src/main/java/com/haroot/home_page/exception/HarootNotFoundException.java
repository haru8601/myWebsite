package com.haroot.home_page.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HarootNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public HarootNotFoundException(String message) {
    this(message, null);
  }

  public HarootNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }
}
