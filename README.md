# haroot.net

haroot のホームページ。リンクは[こちら](https://haroot.net)。

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

#### 依存関係の確認

```sh
mvn dependency:tree 2>/dev/null | grep "spring-boot:"
```

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

#### レスポンシブ

- レスポンシブの境界は`768px`とする
  - iPadなどはPCサイズ, iPhoneはSPサイズとなる
- 可能な限りbootstrapのBreakpointsである`md`を使用する

## ライブラリ管理

- `npm`で管理
- インストール時に`Takumi Guard`で悪意のあるパッケージをブロック

## Datadog APM

同じサーバーにdatadog-agentを入れることで \
Datadogにデータを転送している。
`/etc/systemd/system/tomcat.service`にDatadog用の環境変数を渡している。

# デプロイ

## 注意

mvnコマンドは**ローカルのjavaを使ってビルドする**ため、 \
ローカルのjdkを本番のjdkに合わせる必要がある。

## 1. パッケージ化

### cliの場合

```sh
mvn clean package
```

`/target`に war ファイルが生成される

### VSCodeの場合

`Maven: Execute Commands...` > `package`

`/target`に war ファイルが生成される

### ローカル確認

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
```

## 2. デプロイ手順

1. warファイルを本番環境にコピー
2. 下記コマンドでssh接続
   ```sh
   ssh -i 対象のpem [ユーザー名@パブリックIP]
   ```
3. 下記コマンドでフォルダ移動やBE再起動
   ```sh
   sudo su
   cd /var/lib/tomcat10/webapps
   # 古いwarファイルのバックアップ
   mv ROOT.war ../backup/ROOT_${今日の日付}.war
   # 新しいwarファイルの配置
   mv /home/ec2-user/作成したwar ./ROOT.war
   systemctl status tomcat10
   systemctl restart tomcat10
   ```

## 記事画像

記事のサムネイル画像は、
本番アプリケーションから保存するため
直接`/var/www/html/images`に配置します。

- Apacheの設定(`/etc/httpd/conf/httpd.conf`)で、`/var/www/html`配下の静的リソースを公開しています。

# 運用

## スケジューラー

1日1回サーバーを再起動する(systemd-timer経由)。

```sh
# 登録されているタイマー一覧を確認
systemctl list-timers
# タイマーの編集
vi /etc/systemd/system/tomcat-lifecycle.timer
# 実行コマンドの編集
vi /etc/systemd/system/tomcat-lifecycle.service
```

## ログ確認

[application-production.yml](application-production.yml)で指定された`path.log`にアプリケーションのログを格納している。

# ドメイン

## WORK

作品一覧

- WORKSだと意味が変わってしまうのでDB上も`work`テーブルとしている。
