package com.haroot.home_page.exception;

public class HarootFraudeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public HarootFraudeException(String message) {
    this(message, null);
  }

  public HarootFraudeException(String message, Throwable ex) {
    super(message, ex);
  }

}
