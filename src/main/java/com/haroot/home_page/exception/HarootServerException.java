package com.haroot.home_page.exception;

public class HarootServerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public HarootServerException(String message) {
    this(message, null);
  }

  public HarootServerException(String message, Throwable ex) {
    super(message, ex);
  }
}
