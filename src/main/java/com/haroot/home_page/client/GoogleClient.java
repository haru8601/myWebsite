package com.haroot.home_page.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.haroot.home_page.entity.RecaptchaVerifyEntity;

@HttpExchange
public interface GoogleClient {
    @PostExchange("/recaptcha/api/siteverify")
    RecaptchaVerifyEntity verifyRecaptcha(
            @RequestParam String secret,
            @RequestParam String response,
            @RequestParam String remoteip);
}
