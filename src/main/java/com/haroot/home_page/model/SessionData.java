package com.haroot.home_page.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionData {
	// トップページ訪問済みフラグ
	private boolean visited;
	// ビンゴ数(ビンゴでなければ-1、ビンゴなら1~3)
	private String bingoImg;

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean getVisited() {
		return visited;
	}

	public void setBingoImg(String bingoImg) {
		this.bingoImg = bingoImg;
	}

	public String getBingoImg() {
		return bingoImg;
	}

	SessionData() {
		bingoImg = "";
		visited = false;
	}
}
