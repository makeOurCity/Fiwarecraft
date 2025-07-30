# Pluginの使用開始方法

## 1. jarファイルの配置

以下でビルドします。

```console
mvn clean package -DskipTests=true
```

ビルド成功したら `target/fiwarecraft-x.x-SNAPSHOT.jar` ファイルを、paper MCのディレクトリのpluginディレクトリ下に配置します。

## 2. launch.shの編集

環境変数を <https://mypage.sandbox.makeour.city/> の `接続情報` から得る。

```bash
#/bin/sh

export TEST_COGNITO_USER_POOL_ID=""  # USER_POOL_ID
export TEST_COGNITO_CLIENT_ID=""     # APP_CLIENT_ID
export TEST_COGNITO_USERNAME=""      # Log in username
export TEST_COGNITO_PASSWORD=""      # log in password

java -Xms2G -Xmx2G -jar paper-1.21.4-231.jar 
```

## 3. 起動

その後 paper MCのサーバーを launch.sh 経由で起動してください。
