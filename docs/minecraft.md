# Minecraftの準備

## 1. paperサーバーのダウンロード・起動

<https://papermc.io/downloads/paper> よりダウンロード。

以下を参考に `launch.sh` の作成

```sh
#/bin/sh
java -Xms2G -Xmx2G -jar paper-1.21.4-231.jar 
```

一回実行してファイルが作成されるのを確認。

`eula.txt` の中身を以下のように変更。
（falseをtrueにする）

```txt
eula=true
```

`launch.sh` を再度実行する。

## 2. マインクラフトの起動・paperへの接続

<https://www.minecraft.net/ja-jp/download> よりマインクラフトをダウンロード。

Java Editionの、起動構成のタブに移動する。

![boot](<img/minecraft-java.png>)

新規作成をして、以下のような接続設定で起動する。

![boot](<img/connection.png>)

「プレイ」をクリックし、起動したら、「マルチプレイ」を選択。

![boot](<img/multiplay.png>)

サーバーが見つかると以下のような画面になるので、「サーバーに接続」をクリックしてゲームを開始する。

![boot](<img/spigot-server.png>)

### サーバーが見つからない場合

以下のように、 `サーバーを追加` をクリックして以下の情報を入れてください。

| 項目 | 値 |
| --- | -- |
| サーバー名 | fiwarecraft |
| サーバーアドレス | 127.0.0.1 |

![servers](<img/servers.png>)

![servers](<img/add_servers.png>)
