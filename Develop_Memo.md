# develop memo  
108-2物件導向程式設計期末專題  
<a href= https://github.com/jc-hiroto/java-2020-final/blob/master/docs/108-2%20OOP%20Final%20Project%20v0309.pdf>Project PDF</a>
## 開發項目
### 資料庫串接  
@RaymondKao
  - 加入現有資料
  - 訂單資料紀錄
  - 會員資料紀錄（選用）
  - 我的最愛紀錄（選用）
### 後端資料處理  
@Tetrapod1206
  - 擷取旅程資料，同步到介面顯示處
  - 更改訂單之動作
  - 帳號密碼登入驗證
  - 各項欄位填寫（存入資料庫前）的驗證（確保不溢位或阻止不合法之輸入）
### GUI  
@jc-hiroto 
  - 首頁
  - 訂單管理頁面
  - 搜尋行程功能
  - 會員登入頁面（選用）
  - 會員註冊頁面（選用）
  - 我的最愛頁面（選用）
  - 推薦行程頁面（選用）
### Android 系統移植  
@kenny950292
  - GUI移植
  - 多解析度、顯示方向支援
  - 服務移植
### 簡報  
@chewei-hsu
  - 系統demo
  - 程式解釋
  - 開發過程

## 開發進度

### 資料庫  
@RaymondKao

### 後端資料處理  
@Tetrapod1206

### GUI  
@jc-hiroto 
- 20200323: GUI Test
    - 介面製作測試
    - 開始製作首頁
- 20200323-2: GUI update
    - 首頁製作完成
- 20200330: GUI update
    - 開始製作登入與關於頁面
- GUI Update: Error
    - 我把介面的code搞爛了
    - 可編譯執行但會產生 null pointer 錯誤
- Restore: GUI
    - 還原版本
- Restore: GUI
    - 還原版本
- Restore: Fix GUI crash.
    - 發現 null pointer 錯誤是因為在 form 處選擇 custom create 但是 java檔案中沒有相應的程式碼可參考
    - 新舊版本進行手動合併後，還原版本
- 20200330-1: GUI Update
    - 更新登入頁面與關於頁面
- 20200330-2: GUI fix
    - 修正不同功能頁面切換的方式修正
- 20200330-3: GUI update.
    - 
- 20200320-4: GUI update.
- 20200331: GUI update
    - 增加簡單的登入與註冊輸入值檢查
    - 增加登入成功畫面
    - 增加登入失敗畫面

### Android 移植  
@kenny950292

### 簡報  
@chewei-hsu