# haroot.net
harootのホームページ。リンクは[こちら](https://haroot.net)。

## 技術要素
| 技術                   | バージョン  | description                      |
| -------------------- | ------ | -------------------------------- |
| Java                 | 17.0.3 | 開発言語                             |
| Spring Boot          | 2.5.6  | フレームワーク                          |
| Thymeleaf            | 2.5.6  | 画面テンプレート                                 |
| maven                | 4.0.0  | Java用ビルドツール                      |
| SpringToolSuite      | 4.14.0 | Eclipseを拡張したSpring用開発ツール         |
| Ascii Tree Generator | 1.2.4  | markdownでのファイルtree生成(VSCode拡張機能) |
| Text Tables          | 0.1.5  | markdownでのテーブル編集(VSCode拡張機能)     |
<br>

## application.yaml
下記のプロパティを含む必要があります。
```sh
.
├── spring
│   ├── profiles
│   │   └── active # local
│   ├── jpa
│   │   └── open-in-view # false
│   └── mail
│       ├── host # 略
│       ├── port # 587
│       ├── username # 略
│       ├── password # 略
│       └── properties
│           └── mail
│               └── smtp
│                   ├── auth # true
│                   └── starttls
│                       └── enable # true
├── youtube
│   ├── key # 略
│   └── playlistId # UUXXXXXaaaaa44444RRRRRzz
├── qiita
│   ├── token # 略
│   └── user # haru8601
└── user
    ├── username # root
    └── password # password
```