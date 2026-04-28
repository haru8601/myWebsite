package com.haroot.home_page.enums;

import lombok.Getter;

@Getter
public enum TopicEnum {
  ABOUT("ABOUT", "経歴", "about", "harootとは...?", "/icon/about.svg"),
  WORK("WORK", "ワークス", "work", "個人で製作した作品一覧です。", "/icon/work.svg"),
  ARTICLES("ARTICLES", "技術記事", "articles", "IT周りの自分用にまとめた記事一覧です。", "/icon/articles.svg"),
  CONTACT("CONTACT", "お問い合わせ", "contact", "お問い合わせフォームはこちら。", "/icon/contact.svg");

  private final String title;
  private final String titleJp;
  private final String url;
  private final String summary;
  private final String imagePath;

  private TopicEnum(String title, String titleJp, String url, String summary, String imagePath) {
    this.title = title;
    this.titleJp = titleJp;
    this.url = url;
    this.summary = summary;
    this.imagePath = imagePath;
  }
}
