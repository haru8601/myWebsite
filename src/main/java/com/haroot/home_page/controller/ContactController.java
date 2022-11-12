package com.haroot.home_page.controller;

import java.util.regex.Pattern;  

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.logic.DateLogic;
import com.haroot.home_page.model.FormData;

import lombok.RequiredArgsConstructor;

/**
 * 問い合わせコントローラー
 * @author sekiharuhito
 *
 */
@Controller
@RequiredArgsConstructor
public class ContactController {

	private final MailSender sender;
	final JdbcTemplate jdbcT;

	/**
	 * 問い合わせ画面表示
	 * @param mav MAV
	 * @return
	 */
	@GetMapping("/contact")
	public ModelAndView contactLink(ModelAndView mav) {
		FormData formData = new FormData();
		mav.addObject("formData", formData);

		mav.setViewName("contents/contact");
		return mav;
	}

	/**
	 * 問い合わせ送信
	 * @param formData 問い合わせフォーム
	 * @param bindingResult エラー結果
	 * @param request リクエスト
	 * @param mav MAV
	 * @return
	 */
	@PostMapping("/sentForm")
	public ModelAndView postContact(
			@ModelAttribute @Validated FormData formData,
			BindingResult bindingResult,
			HttpServletRequest request,
			ModelAndView mav) {
		Pattern mailP = Pattern.compile("[a-zA-Z0-9]+@([a-zA-Z0-9]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}");
		Pattern urlP = Pattern.compile("https?://.*\\.");
		// その他のエラー
		Pattern elseP = Pattern.compile("@Cryptaxbot");
		// お問い合わせ内容にメールアドレスやリンクが含まれていたらエラーを追加
		if (mailP.matcher(formData.getContent()).find()) {
			FieldError fieldError = new FieldError("formData", "content", "メールアドレスを含めることはできません");
			bindingResult.addError(fieldError);

		} else if (urlP.matcher(formData.getContent()).find()) {
			FieldError fieldError = new FieldError("formData", "content", "リンクを含めることはできません");
			bindingResult.addError(fieldError);
		} else if (elseP.matcher(formData.getContent()).find()) {
			FieldError fieldError = new FieldError("formData", "content", "文章を変更してください");
			bindingResult.addError(fieldError);
		}

		// エラーがあれば戻る
		if (bindingResult.hasErrors()) {
			mav.addObject("formData", formData);
			mav.setViewName("contents/contact");
			return mav;
		}

		// メール送信
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("noreply@haroot.net");
		msg.setTo("haroot.net@gmail.com");
		msg.setSubject("【通知】お問い合わせがありました");
		String br = System.getProperty("line.separator");
		String name = formData.getName();
		String email = formData.getEmail();
		String content = formData.getContent();
		String message = name + "さんからお問い合わせがありました。" + br + br + "メールアドレス: "
				+ email + br + br + "お問い合わせ内容: " + br + content;
		msg.setText(message);
		this.sender.send(msg);

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		// DBに保存
		String dateStr = DateLogic.getJSTDateStr();
		String sqlStr = "INSERT INTO contacts(name, email, content, ip, create_date) VALUES(?,?,?,?,?)";
		jdbcT.update(sqlStr, name, email, content, ip, dateStr);

		mav.setViewName("contents/sent");
		return mav;
	}
}
