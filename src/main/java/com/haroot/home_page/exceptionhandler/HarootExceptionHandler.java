package com.haroot.home_page.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.haroot.home_page.exception.HarootNotFoundException;

@ControllerAdvice
public class HarootExceptionHandler {
    @ExceptionHandler(HarootNotFoundException.class)
    public String handleHarootNotFoundException(HarootNotFoundException ex) {
        return "/error/404";
    }
}
