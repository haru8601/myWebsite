package com.haroot.home_page.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class BingoData {
	private String bingoImg;

	public void setBingoImg(String bingoImg) {
		this.bingoImg = bingoImg;
	}

	public String getBingoImg() {
		return bingoImg;
	}

	BingoData() {
		bingoImg = "";
	}
}
