package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class home {
    private JPanel panel1;
    private JButton btnSearch;
    private JTextField searchField;
    private JButton btnRecommand;
    private JButton btnStar;
    private JButton btnManage;
    private JButton btnExit;
    private JButton btnSettings;
    private JButton btnInfo;
    private JButton btnLogin;
    private JPanel Home;
    private JPanel bottom;
    private JPanel homeContentHolder;
    private JPanel loginPanel;
    private JPanel homePanel;
    private JPanel cardHolder;
    private JPanel aboutPanel;
    private JButton btnHome;
    private JButton btnAbout;
    private JPanel registerPanel;
    public static String metalUI = "javax.swing.plaf.metal.MetalLookAndFeel";
    public CardLayout layout = null;
    private JPanel window;
    private JPanel settingsPanel;
    private JButton btnLogout;
    private JPanel recommendPanel;
    private JPanel recommendHolder;
    private JButton prevButton;
    private JButton nextButton;
    private JPanel RecommendListHolder;
    private JPanel RecommendList1;
    private JPanel RecommendList2;
    private JPanel RListObj2_1;
    private JPanel RListObj2_2;
    private JPanel RListObj2_3;
    private JPanel RListObj2_4;
    private JPanel RListObj2_5;
    private JPanel RListObj1_1;
    private JButton 詳情Button;
    private JPanel managePanel;
    private JTextArea textObjR1_1;
    private JTabbedPane tabbedPane1;
    private JTable table1;


    public home() {
        cardInit(); // 初始化各頁面
        btnHome.setVisible(false); // 隱藏回首頁按鈕
        loginButtonInit(); // 根據登入狀況設定帳戶按鈕

        // ================ 以下皆為按鈕動作監聽函數，用來管理按鈕動作 ================ //
        btnExit.addActionListener(new ActionListener() {
            @Override
            // 離開按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            // 搜尋按鈕
            // TODO: 串接搜尋 method，並跳轉到搜尋結果頁面
            public void actionPerformed(ActionEvent actionEvent) {
                String searchContent = getSearchField();
                System.out.println("Search: "+searchContent);
            }
        });
        btnRecommand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                initRecommendContent();
                layout.show(cardHolder, "Recommend");
                exitFromHome();
            }
        });
        btnStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        btnManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                initManageTable();
                layout.show(cardHolder, "Manage");
                exitFromHome();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent){
                refreshLoginPanel();
                layout.show(cardHolder, "Login");
                exitFromHome();
            }
        });
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                backToHome();
            }
        });
        btnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(cardHolder,"About");
                exitFromHome();
            }
        });
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(cardHolder,"Settings");
                exitFromHome();
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginButtonInit();
                layout.show(cardHolder,"Login");
                exitFromHome();
            }
        });
        window.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                textObjR1_1.setText(src.processor.textLineShifter(textObjR1_1,textObjR1_1.getText(),window.getWidth(),10,200));
            }
        });
        // ================ 以上皆為按鈕動作監聽函數，用來管理按鈕動作 ================ //
    }
    public void initRecommendContent(){
        String labelR_1_1 = "[春櫻紛飛遊釜慶]世界文化遺產~佛國寺、CNN評選賞櫻推薦~余佐川羅曼史橋+慶和火車站、甘川洞彩繪壁畫村、BIFF廣場+南浦洞購物樂五日<含稅>";
        textObjR1_1.setFont(textObjR1_1.getFont().deriveFont(15f));
        textObjR1_1.setText(labelR_1_1);
        textObjR1_1.setText(src.processor.textLineShifter(textObjR1_1,textObjR1_1.getText(),window.getWidth(),10,200));
    }
    public void cardInit(){
        // 初始化所有頁面
        cardHolder.add(homePanel, "Home");
        cardHolder.add(loginPanel, "Login");
        cardHolder.add(registerPanel, "Register");
        cardHolder.add(aboutPanel, "About");
        cardHolder.add(settingsPanel, "Settings");
        cardHolder.add(recommendPanel, "Recommend");
        cardHolder.add(managePanel, "Manage");
        layout = (CardLayout)cardHolder.getLayout();
    }

    public void backToHome(){
        // 返回主畫面的動作（包含切換及按鈕可見度設定）
        layout.show(cardHolder, "Home");
        btnHome.setVisible(false);
        btnAbout.setVisible(true);
        btnSettings.setVisible(true);
        loginButtonInit();
    }

    public void exitFromHome(){
        // 返回主畫面的動作（僅按鈕可見度設定）
        btnHome.setVisible(true);
        btnLogin.setVisible(false);
        btnLogout.setVisible(false);
        btnSettings.setVisible(false);
        btnAbout.setVisible(false);

    }
    public void refreshLoginPanel(){
        boolean loginTemp = login.getLoginStatus();
        String userNameTemp = login.getUserName();
        cardHolder.remove(loginPanel);
        loginPanel = new login().getPanel();
        login.setLoginStatus(loginTemp);
        login.setUserName(userNameTemp);
        cardHolder.add(loginPanel,"Login");
        cardInit();
    }
    public void loginButtonInit(){
        // 根據登入情況決定會員按鈕顯示（登入／登出）
        if(login.getLoginStatus()){
            btnLogin.setVisible(false);
            btnLogout.setVisible(true);
            btnLogout.setText(login.getUserName());
        }
        else{
            btnLogin.setVisible(true);
            btnLogout.setVisible(false);
            btnLogout.setText("");
        }
    }
    public void initManageTable(){
        // 初始化形成管理頁面的表格
        // TODO: 寫字串處理method，然後把DB串起來。
        // ================= 以下是資料運用範例 =================
        Object[][] data={
                {"A20200401001","馬達加斯加 猴麵包樹 夢幻生態天堂10天", "2020-04-23","2020-05-02","158900","訂單成立"},
                {"A20200401002","【波蘭、波羅的海三小國、俄羅斯】精彩12日","2020-07-16","2020-07-27","79900","訂單成立"},
                {"A20200401003","【國航假期】東歐純情三國+波蘭8日", "2020-04-26","2020-05-03","47300","訂單處理中"},
                {"A20200401004","《日本嚴選》四國鐵道千年物語•夢幻天空之鏡•高知食彩5日", "2020-07-11","2020-07-15","58900","訂單處理中"}};
        String[] columns={"訂單序號","名稱","出發日期","結束日期","價格", "訂單狀態"};
        table1 = new JTable(data,columns);
    }

    public String getSearchField(){
        // 拿搜尋欄字串資料
        return searchField.getText();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(home.metalUI); // 使用Metal UI 模式啟動
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Find a Place"); // 設定視窗標題
        frame.setSize(1000,800); // 設定初始視窗大小
        frame.setContentPane(new home().window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void createUIComponents() {
        // 自訂設定區域（有勾選 customCreate 的都要加在這裡，不然會產生 nullPointer 錯誤）
        loginPanel = new login().getPanel(); // loginPanel直接呼叫login.java的頁面
        aboutPanel = new about().getPanel(); // 同上
        settingsPanel = new settings().getPanel(); // 同上
        initManageTable(); // 初始化形成管理頁面的表格
        // TODO: 讓表格 jTable 無法被編輯，研究中。
        // public boolean isCellEditable(int row, int column){return false;}
    }
}
