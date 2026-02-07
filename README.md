# haroot.net

haroot のホームページ。リンクは[こちら](https://haroot.net)。

# 技術要素

| 技術                 | バージョン | description                                      |
| -------------------- | ---------- | ------------------------------------------------ |
| Java                 | 17.0.3     | 開発言語                                         |
| Spring Boot          | 3.0.1      | フレームワーク                                   |
| Thymeleaf            | 3.0.1      | 画面テンプレート                                 |
| maven                | 4.0.0      | Java 用ビルドツール                              |
| SpringToolSuite      | 4.14.0     | Eclipse を拡張した Spring 用開発ツール           |
| Ascii Tree Generator | 1.2.4      | markdown でのファイル tree 生成(VSCode 拡張機能) |
| Text Tables          | 0.1.5      | markdown でのテーブル編集(VSCode 拡張機能)       |

<br>

# 環境構築

## application.yaml

下記のプロパティを含む必要があります。

```sh
.
├── spring
│   ├── profiles
│   │   └── active # local
│   ├── jpa
│   │   ├── open-in-view # false(optional)
│   │   └── properties
│   │       └── jakarta
│   │           └── persistence
│   │              └── sharedCache
│   │                  └── mode # ENABLE_SELECTIVE(optional)
│   ├── datasource
│   │   ├── url # jdbc:mysql://AAAAA:3306/BBB
│   │   ├── username # admin
│   │   ├── password # password
│   │   └── driver-class-name # com.mysql.cj.jdbc.Driver
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
├── user
│   ├── username # root
│   └── password # password
└── path
    ├── log # /hoge/log/app.log
    ├── site # haroot.net
    └── resource # /hoge/aaa
```

# デプロイ

`pom.xml`を開き、Run As > Maven Build<br>

`/target`に war ファイルが生成される

## 画像ファイル

記事のサムネイル画像は直接、`/var/www/html/images/articles/{id}/thumbnail.png`に配置します。
