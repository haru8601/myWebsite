package com.haroot.home_page.enums;

import lombok.Getter;

@Getter
public enum TopicEnum {
  IOS_APP("iOS Apps", "iOSアプリ", "ios-app", "個人で製作したiOSアプリ一覧です。", true),
  ARTICLES("Articles", "技術記事", "articles", "IT周りの自分用にまとめた記事一覧です。", true),
  MUSIC("Music", "音楽", "music", "耳コピしたものとか置いてます。", true),
  POKE("Poké", "ポケモン", "poke", "ポケモン関係(もちろん非公式)のプロダクトです。", true),
  OTHERS("Others", "その他", "others", "その他のコンテンツです", true),
  ABOUT("About", "経歴", "about", "harootとは...?", false),
  CONTACT("Contact", "お問い合わせ", "contact", "お問い合わせフォームはこちら。", false);

  private final String title;
  private final String titleJp;
  private final String url;
  private final String summary;
  private final boolean displayInTop;

  private TopicEnum(String title, String titleJp, String url, String summary, boolean displayInTop) {
    this.title = title;
    this.titleJp = titleJp;
    this.url = url;
    this.summary = summary;
    this.displayInTop = displayInTop;
  }
}
