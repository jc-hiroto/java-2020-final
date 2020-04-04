# development memo  
108-2物件導向程式設計期末專題  
## Menu
[TOC]
## Info
### Project PDF
{%pdf https://raw.githubusercontent.com/jc-hiroto/java-2020-final/master/docs/108-2%20OOP%20Final%20Project%20v0309.pdf%}
<a href= https://github.com/jc-hiroto/java-2020-final/blob/master/docs/108-2%20OOP%20Final%20Project%20v0309.pdf>source</a>
### Project Authors
- 張博皓 ` @jc-hiroto `
- 蕭博瀚 `@Tetrapod1206`
- 高睿 `@RaymondKao`
- 安彥百 `@kenny950292`
- 許哲維 `@chewei-hsu`
### Libraries used
#### 介面
- javax.swing.*
- java.awt.*
#### 資料庫 (使用SQLite)
- java.sql.Connection
- java.sql.DriverManager
- java.sql.SQLException
- sqlite-jdbc-3.30.1
#### 加密
- javax.crypto.Cipher
- javax.crypto.spec.SecretKeySpec
- java.security.Security
- org.bouncycastle.jce.provider.BouncyCastleProvider
- org.apache.commons.codec.binary.Base64.decodeBase64
- org.apache.commons.codec.binary.Base64.encodeBase64

#### 驗證
- org.apache.commons.validator.routines.EmailValidator

#### JSON 解析
- org.json.simple.JSONArray
- org.json.simple.JSONObject
- org.json.JSONException
- org.json.simple.parser.JSONParser
- org.json.simple.parser.ParseException
### Policy
- Icon provided by <a href= https://www.flaticon.com>Flaticon</a>

## 開發項目
### 資料庫串接  
`@RaymondKao`
  - 加入現有資料
      - SQLite + java + csv 匯入
  - 訂單資料紀錄
    - 連線到資料庫: <a href= https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver>SQLite Java: Connect To The SQLite Database Using SQLite JDBC Driver</a>
    - 加入新資料 INSERT: <a href= https://www.sqlitetutorial.net/sqlite-java/insert>SQLite Java: Inserting Data</a>
    - 選擇並提取資料 SELECT: <a href= https://www.sqlitetutorial.net/sqlite-java/select>SQLite Java: Select Data</a>
  - 會員資料紀錄（選用）
      - 密碼採用 AES 加密
      - SQL 語法紀錄
      ```java
        public static String getInsertSql(String userName,String userEmail,StringBuffer userPass) {
            return  "INSERT INTO USER (USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES ("+userName+",'"+userEmail+"',"+userPass.toString()+");";
        }
      ```
  - 我的最愛紀錄（選用）
### 後端資料處理  
`@Tetrapod1206`
  - 擷取旅程資料，同步到介面顯示處
  - 更改訂單之動作
  - 帳號密碼登入驗證
  - 各項欄位填寫（存入資料庫前）的驗證（確保不溢位或阻止不合法之輸入）
### GUI  
`@jc-hiroto`
  - 首頁
  - 訂單管理頁面
    - 表單顯示與標題換行（研究中）
        - <a href= https://codertw.com/程式語言/299633>Jlabel實現內容自動換行簡單例項</a>
        - <a href=https://stackoverflow.com/questions/10432385/how-to-make-a-jtable-not-editable-in-java>stackoverflow - How to make a jtable not editable in java?
</a>
        - <a href=http://yhhuang1966.blogspot.com/2014/05/java-swing-jtable.html>Java Swing 測試 : 表格 JTable</a>
  - 搜尋行程功能
  - 會員登入頁面（選用）
  - 會員註冊頁面（選用）
  - 我的最愛頁面（選用）
  - 推薦行程頁面（選用）
### Android 系統移植  
`@kenny950292`
  - GUI移植
  - 多解析度、顯示方向支援
  - 服務移植
### 簡報  
`@chewei-hsu`
  - 系統demo
  - 程式解釋
  - 開發過程

## 開發進度

### 資料庫  
`@RaymondKao`
`master`
- `master` 20200404: password hash/ Json parser update
:::info
- 增加加密與JSON提取程式
:::
- `master` 20200404: Create SQLite DB and connection code `@jc-hiroto`
:::success
- 已匯入csv中旅遊資料至SQLite DB中 (DB/trip_app.db)
- 完成連線到資料庫的程式 (db.java)
:::
- `master` 20200404-02: implement AES encrypt `@jc-hiroto`
:::info
- 串接AES加密至登入與註冊頁面
:::
:::danger
- 有關於密碼的字串傳遞都將使用StringBuffer，增加安全性（getPassword char[] to StringBuffer 尚未解決，仍須以 String 為中繼）
:::
- `master` 20200404-03: DB update
:::info
- 加入SQLite新增資料語法（註冊部分）
:::

### 後端資料處理  
`@Tetrapod1206`
- `master` 20200325: set the definition of all process method
:::info
- 增加後端功能與資料庫用 java 檔 (processor.java, db.java)
:::
- `mailValidChecker` 2020/4/4 I broke the program
:::warning
- 引入了外部的檢查函式，所有東西都壞掉了
:::
- `mailValidChecker` 20200404: Fix package problem `@jc-hiroto`
:::success
- 解決 apache validator 包問題
- 同步所有 .jar 檔
:::
### GUI  
`@jc-hiroto`
- `master` 20200323: GUI Test
:::info
- 介面製作測試
- 開始製作首頁
:::
- `master` 20200323-2: GUI update
:::success
- 首頁製作完成
:::
- `master` 20200330: GUI update
:::info
- 開始製作登入與關於頁面 (login.form / ~.java) (about.form / ~.java)
:::
- `master` GUI Update: Error
:::warning
- 我把介面的code搞爛了
- 可編譯執行但會產生 null pointer 錯誤
:::
- `master` Restore: GUI
:::warning
- 還原版本
:::
- `master` Restore: GUI
:::warning
- 還原版本
:::
- `master` Restore: Fix GUI crash.
:::warning
- 發現 null pointer 錯誤是因為在 form 處選擇 custom create 但是 java檔案中沒有相應的程式碼可參考
- 新舊版本進行手動合併後，還原版本
:::
- `master` 20200330-1: GUI Update
:::info
- 更新登入頁面與關於頁面 (login.form / ~.java) (about.form / ~.java)
:::
- `master` 20200330-2: GUI fix
:::info
- 修正不同功能頁面切換的方式
:::
- `master` 20200330-3: GUI update.
:::info
- 增加註冊頁面（register.form / ~.java）
:::
- `master` 20200320-4: GUI update.
:::info
- 增加設定頁面
:::
- `master` 20200331: GUI update
:::info
- 增加簡單的登入與註冊輸入值檢查
- 增加登入成功畫面 (in login.form / ~.java)
- 增加登入失敗畫面 (in login.form / ~.java)
:::
- `master` 20200401: GUI update, add comments
:::info
- 新增註解 (home.form / ~.java)
- 開始製作推薦行程頁面 (in home.form / ~.java)
- 新增行程管理頁面 (in home.form / ~.java)
- 愚人節快樂 :stuck_out_tongue_winking_eye: 
:::
### Android 移植  
`@kenny950292`

### 簡報  
`@chewei-hsu`