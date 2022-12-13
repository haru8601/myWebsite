package com.haroot.home_page.controller;

import java.util.regex.Pattern;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.FormDto;
import com.haroot.home_page.service.ContactService;

import lombok.RequiredArgsConstructor;

/**
 * 問い合わせコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final MailSender sender;
    final ContactService contactService;

    /**
     * 問い合わせ画面表示
     * 
     * @param mav MAV
     * @return
     */
    @GetMapping
    public ModelAndView contactLink(ModelAndView mav) {
        FormDto formDto = new FormDto();
        mav.addObject("formDto", formDto);

        mav.setViewName("contents/contact");
        return mav;
    }

    /**
     * 問い合わせ送信
     * 
     * @param formData      問い合わせフォーム
     * @param bindingResult エラー結果
     * @param request       リクエスト
     * @param mav           MAV
     * @return
     */
    @PostMapping("send")
    public ModelAndView postContact(
            @ModelAttribute @Validated FormDto formDto,
            BindingResult bindingResult,
            ModelAndView mav) {
        Pattern mailP = Pattern.compile("[a-zA-Z0-9]+@([a-zA-Z0-9]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}");
        Pattern urlP = Pattern.compile("https?://.*\\.");
        // その他のエラー
        Pattern elseP = Pattern.compile("@Cryptaxbot");
        // お問い合わせ内容にメールアドレスやリンクが含まれていたらエラーを追加
        if (mailP.matcher(formDto.getContent()).find()) {
            FieldError fieldError = new FieldError("formData", "content", "メールアドレスを含めることはできません");
            bindingResult.addError(fieldError);

        } else if (urlP.matcher(formDto.getContent()).find()) {
            FieldError fieldError = new FieldError("formData", "content", "リンクを含めることはできません");
            bindingResult.addError(fieldError);
        } else if (elseP.matcher(formDto.getContent()).find()) {
            FieldError fieldError = new FieldError("formData", "content", "文章を変更してください");
            bindingResult.addError(fieldError);
        }

        // エラーがあれば戻る
        if (bindingResult.hasErrors()) {
            mav.addObject("formDto", formDto);
            mav.setViewName("contents/contact");
            return mav;
        }

        // メール送信
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("noreply@haroot.net");
        msg.setTo("haroot.net@gmail.com");
        msg.setSubject("【通知】お問い合わせがありました");
        String br = System.getProperty("line.separator");
        String message = formDto.getName() + "さんからお問い合わせがありました。" + br + br + "メールアドレス: "
                + formDto.getEmail() + br + br + "お問い合わせ内容: " + br + formDto.getContent();
        msg.setText(message);
        this.sender.send(msg);

        // DBに保存
        contactService.register(formDto);

        mav.setViewName("contents/sent");
        return mav;
    }
}
