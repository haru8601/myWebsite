package com.haroot.home_page.enums;

import lombok.Getter;

@Getter
public enum TopicEnum {
  IOS_APP("iOS Apps", "iOSアプリ", "ios-app", "個人で製作したiOSアプリ一覧です。"),
  ARTICLES("Articles", "技術記事", "articles", "IT周りの自分用にまとめた記事一覧です。"),
  MUSIC("Music", "音楽", "music", "耳コピしたものとか置いてます。"),
  POKE("Poké", "ポ○モン", "poke", "ポ○モン関係(もちろん非公式)のプロダクトです。"),
  ABOUT("About", "経歴", "about", "harootとは...?"),
  CONTACT("Contact", "お問い合わせ", "contact", "お問い合わせフォームはこちら。");

  private final String title;
  private final String titleJp;
  private final String url;
  private final String summary;

  private TopicEnum(String title, String titleJp, String url, String summary) {
    this.title = title;
    this.titleJp = titleJp;
    this.url = url;
    this.summary = summary;
  }
}
