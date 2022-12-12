package com.haroot.home_page.exception;

public class HarootNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HarootNotFoundException(String message) {
        this(message, null);
    }

    public HarootNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
