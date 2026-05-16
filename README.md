# haroot.net

haroot のホームページ。リンクは[こちら](https://haroot.net)。

# 技術要素

- BE: Java, SpringBoot, Spring Data JPA, Thymeleaf, maven
- FE: TypeScript, scss, webpack, Prettier, biome, npm
- インフラ: EC2, MySQL, GCP(YouTube API, reCAPTCHA)

# 開発

## BE

### java

sdkmanでバージョンを管理

```sh
sdk list java
```

### maven

`pom.xml`を変更したら \
VSCodeで`cmd`+`shift`+`P` > `Java: Reload Projects`

### ORM

Spring Data JPAを使用。

- DB規模が小さい前提
- JOINも基本用いず、ロジック側で結合、マッピングを行う
- パフォーマンスが落ちる場合、別のORMを検討する

## FE

### thymeleaf

- `thymeleaf`のfragmentを用いて、ヘッダーやフッターをコンポーネント化している
- `thymeleaf-layout-dialect`のdecorateを用いて、\
  各ページをlayout.htmlの一部として逆importさせている

### css

#### 規則

- 基本はbootstrapのクラスを使用
- 独自のクラス名を使う場合、`hr-`のprefixを付ける

#### コンパイル

```sh
npm run scss
```

## ライブラリ管理

- `npm`で管理
- インストール時に`Takumi Guard`で悪意のあるパッケージをブロック

# デプロイ

## 注意

mvnコマンドは**ローカルのjavaを使ってビルドする**ため、 \
ローカルのjdkを本番のjdkに合わせる必要がある。

## パッケージ

### cliの場合

```sh
mvn clean package
```

`/target`に war ファイルが生成される

### VSCodeの場合

`Maven: Execute Commands...` > `package`

`/target`に war ファイルが生成される

### STSの場合

`pom.xml`を開き、Run As > Maven Build<br>

`/target`に war ファイルが生成される

### ローカル確認

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
```

## デプロイ

1. warファイルを本番環境にコピー
2. 下記コマンドでssh接続
   ```sh
   ssh -i 対象のpem [ユーザー名@パブリックIP]
   ```
3. 下記コマンドでフォルダ移動やBE再起動
   ```sh
   sudo su
   cd /opt/tomcat/webapps
   # 古いwarファイルのバックアップ
   mv ROOT.war ../backup/ROOT_${今日の日付}.war
   # 新しいwarファイルの配置
   mv /home/ec2-user/作成したwar ./ROOT.war
   systemctl status tomcat
   systemctl restart tomcat
   ```

## 画像ファイル

記事のサムネイル画像は直接、`/var/www/html/images/articles/{id}/thumbnail.png`に配置します。

# ドメイン

## WORK

作品一覧

- WORKSだと意味が変わってしまうのでDB上も`work`テーブルとしている。
