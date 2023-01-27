package com.haroot.home_page.enums;

import lombok.Getter;

@Getter
public enum TopicEnum {
  IOS_APP("iOS Apps", "ios-app", "個人で製作したiOSアプリ一覧です。"),
  ARTICLES("Articles", "articles", "IT周りの自分用にまとめた記事一覧です。"),
  MUSIC("Music", "music", "耳コピしたものとか置いてます。"),
  POKE("Poké", "poke", "ポケモン関係(もちろん非公式)のプロダクトです。"),
  ABOUT("About", "about", "harootとは...?"),
  CONTACT("Contact", "contact", "お問い合わせフォームはこちら。");

  private final String title;
  private final String url;
  private final String summary;

  private TopicEnum(String title, String url, String summary) {
    this.title = title;
    this.url = url;
    this.summary = summary;
  }
}
