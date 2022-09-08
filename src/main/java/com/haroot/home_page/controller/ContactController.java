package com.haroot.home_page.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.logic.DateLogic;
import com.haroot.home_page.model.FormData;

@Controller
public class ContactController {

	@Autowired
	private MailSender sender;
	@Autowired
	JdbcTemplate jdbcT;

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView getContact(ModelAndView mav) {
		FormData formData = new FormData();
		mav.addObject("formData", formData);

		mav.setViewName("contents/contact");
		return mav;
	}

	@RequestMapping(value = "/sentForm", method = RequestMethod.POST)
	public ModelAndView postContact(
			@ModelAttribute @Validated FormData formData,
			BindingResult errResult,
			HttpServletRequest request,
			ModelAndView mav) {
		Pattern mailP = Pattern.compile("[a-zA-Z0-9]+@([a-zA-Z0-9]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}");
		Pattern urlP = Pattern.compile("https?://.*\\.");
		// お問い合わせ内容にメールアドレスやリンクが含まれていたらエラーを追加
		if (mailP.matcher(formData.getContent()).find()) {
			FieldError fieldError = new FieldError("formData", "content", "メールアドレスを含めることはできません");
			errResult.addError(fieldError);

		} else if (urlP.matcher(formData.getContent()).find()) {
			FieldError fieldError = new FieldError("formData", "content", "リンクを含めることはできません");
			errResult.addError(fieldError);
		}

		// エラーがあれば戻る
		if (errResult.hasErrors()) {
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
