package src;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.jidesoft.swing.RangeSlider;
import com.toedter.calendar.JDateChooser;
import src.hash.searchEngine;
import com.jidesoft.swing.*;
import src.db;

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
    private JPanel old_recommendPanel;
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
    private JButton 看更多Button;
    private JPanel managePanel;
    private JTextArea textObjR1_1;
    private JTable table1;
    private JDateChooser JDateChooser1;
    private JDateChooser JDateChooser2;
    private JCheckBox dateSearch;
    private JPanel errorAlert;
    private JPanel searchResultPanel;
    private JPanel recommendPanel;
    private JCheckBox checkBoxPrice;
    private RangeSlider rangeSliderPrice;
    private JCheckBox checkBoxPeople;
    private RangeSlider rangeSliderPeople;
    private JLabel dateArrow;
    private JLabel priceLowDisplay;
    private JLabel priceHighDisplay;
    private JLabel peopleLowDisplay;
    private JLabel peopleHighDisplay;
    private JComboBox comboBox1;
    private JProgressBar progressBar1;
    private JLabel loading;
    private JPanel reccListHolder;
    private JButton 不知道要去哪Button;
    private JButton 找便宜Button;
    private JButton 找近期Button;
    private JScrollPane tableHolder;
    private JButton btnEdit;
    private JButton btnCancel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ZoneId zoneId = ZoneId.systemDefault();
    private LocalDate localDate = LocalDate.now();
    private ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
    private Date nowDate = Date.from(zdt.toInstant());
    private String searchTemp = "";
    private searchEngine travelCodeSearchEngine = new searchEngine();

    public home() {
        dateChooserInit();
        loading.setVisible(false);
        rangeSliderPrice.setHighValue(100000);
        rangeSliderPrice.setLowValue(30000);
        rangeSliderPeople.setLowValue(25);
        rangeSliderPeople.setHighValue(75);
        rangeSliderPrice.setEnabled(false);
        rangeSliderPeople.setEnabled(false);
        priceLowDisplay.setText("$ "+rangeSliderPrice.getLowValue());
        priceHighDisplay.setText("$ "+rangeSliderPrice.getHighValue());
        peopleLowDisplay.setText(rangeSliderPeople.getLowValue()+" 人");
        peopleHighDisplay.setText(rangeSliderPeople.getHighValue()+" 人");
        priceHighDisplay.setEnabled(false);
        priceLowDisplay.setEnabled(false);
        peopleHighDisplay.setEnabled(false);
        peopleLowDisplay.setEnabled(false);
        cardInit(); // 初始化各頁面
        btnHome.setVisible(false); // 隱藏回首頁按鈕
        loginButtonInit(); // 根據登入狀況設定帳戶按鈕
        JDialog.setDefaultLookAndFeelDecorated(true);
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
                errorAlert.setVisible(false);
                loading.setVisible(true);
                String searchContent = getSearchField();
                boolean isValidDate = true; // 等待傳入日期偵測
                if(!isValidDate){
                   searchContent = "";
                   errorAlert.setVisible(true);
                }
                else{
                    if(searchContent.equals("輸入國家/城市關鍵字")|| searchContent.equals("")){
                        searchContent = "";
                    }
                    System.out.println("Search: "+searchContent);
                    System.out.println("Start Date: "+dateFormat.format(JDateChooser1.getDate()));
                    System.out.println("End Date: "+ dateFormat.format(JDateChooser2.getDate()));
                    System.out.println("date Search?: "+dateSearch.isSelected());
                    String code = travelCodeSearchEngine.searchTravelCode(searchContent);
                    String startDate = dateSearch.isSelected()?dateFormat.format(JDateChooser1.getDate()):"";
                    String endDate = dateSearch.isSelected()?dateFormat.format(JDateChooser2.getDate()):"";
                    int priceTop = checkBoxPrice.isSelected()?rangeSliderPrice.getHighValue():0;
                    int priceBottom = checkBoxPrice.isSelected()?rangeSliderPrice.getLowValue():0;
                    int peopleTop = checkBoxPeople.isSelected()?rangeSliderPeople.getHighValue():0;
                    int peopleBottom = checkBoxPeople.isSelected()?rangeSliderPeople.getLowValue():0;
                    System.out.println(comboBox1.getSelectedIndex());
                    try {
                        searchResultPanel = new JListCustomRenderer().createPanel(db.getResult(code,priceBottom,priceTop,startDate,endDate,peopleBottom,peopleTop,comboBox1.getSelectedIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    cardHolder.remove(searchResultPanel);
                    cardInit();
                    layout.show(cardHolder,"SearchResult");
                    exitFromHome();
                    loading.setVisible(false);
                }
            }
        });
        btnRecommand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardHolder.remove(recommendPanel);
                try {
                    recommendPanel = new JListCustomRenderer().createPanel(db.getResult(Integer.toString(Processor.randomTravelCodeGene()), 0,0,"","",0,0,0));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cardHolder.add(recommendPanel,"Recommend");
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
                if(LoginUser.getUserName() != null){
                    cardHolder.remove(managePanel);

                    try {
                        managePanel = new manage().getPanel();
                        initManageTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    cardHolder.add(managePanel,"Manage");
                    layout.show(cardHolder, "Manage");
                    table1.repaint();
                    AbstractTableModel m = (AbstractTableModel)table1.getModel();
                    m.fireTableDataChanged();
                    tableHolder.setVisible(false);
                    tableHolder.setVisible(true);
                    exitFromHome();
                }
                else{
                    JOptionPane.showMessageDialog(null,"請登入後再查看訂單","您尚未登入！",JOptionPane.WARNING_MESSAGE);
                }

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
        JDateChooser1.getDateEditor().addPropertyChangeListener(
                // if date1 changed, reset date2 limit.
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if ("date".equals(e.getPropertyName())) {
                            JDateChooser2.setMinSelectableDate(JDateChooser1.getDate());
                        }
                    }
                });
        window.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                textObjR1_1.setText(Processor.textLineShifter(textObjR1_1,textObjR1_1.getText(),window.getWidth(),10,200));
            }
        });
        // ================ 以上皆為按鈕動作監聽函數，用來管理按鈕動作 ================ //
        searchFieldListener();
        dateSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dateSearch.isSelected()){
                    JDateChooser1.setEnabled(true);
                    JDateChooser2.setEnabled(true);
                }
                else {
                    JDateChooser1.setEnabled(false);
                    JDateChooser2.setEnabled(false);
                }
            }
        });
        checkBoxPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(checkBoxPrice.isSelected()){
                    rangeSliderPrice.setEnabled(true);
                    priceHighDisplay.setEnabled(true);
                    priceLowDisplay.setEnabled(true);

                }
                else {
                    rangeSliderPrice.setEnabled(false);
                    priceHighDisplay.setEnabled(false);
                    priceLowDisplay.setEnabled(false);
                }
            }
        });
        checkBoxPeople.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(checkBoxPeople.isSelected()){
                    rangeSliderPeople.setEnabled(true);
                    peopleHighDisplay.setEnabled(true);
                    peopleLowDisplay.setEnabled(true);
                }
                else {
                    rangeSliderPeople.setEnabled(false);
                    peopleHighDisplay.setEnabled(false);
                    peopleLowDisplay.setEnabled(false);
                }
            }
        });
        rangeSliderPrice.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                priceLowDisplay.setText("$ "+rangeSliderPrice.getLowValue());
                priceHighDisplay.setText("$ "+rangeSliderPrice.getHighValue());
            }
        });
        rangeSliderPeople.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                peopleLowDisplay.setText(rangeSliderPeople.getLowValue()+" 人");
                peopleHighDisplay.setText(rangeSliderPeople.getHighValue()+" 人");
            }
        });
    }

    public void searchFieldListener(){
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                errorAlert.setVisible(false);
                searchField.setText(searchTemp);
                searchField.setForeground(new Color(8,37,42));
            }
        });

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(searchField.getText().equals("")){
                    searchField.setText("輸入國家/城市關鍵字");
                    searchTemp = "";
                    searchField.setForeground(new Color(175, 175, 175));
                }
                else{
                    searchTemp = searchField.getText();
                }
            }
        });
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                btnEdit.setEnabled(true);
                btnCancel.setEnabled(true);
            }
        });
    }
    public void dateChooserInit(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.DATE, 5); // Date2 offset days
        Date newDate = cal.getTime();
        JDateChooser1.setFont(new Font("Mgen+ 1pp", Font.PLAIN,16));
        JDateChooser2.setFont(new Font("Mgen+ 1pp", Font.PLAIN,16));
        JDateChooser1.setDate(nowDate);
        JDateChooser2.setDate(newDate);
        JDateChooser2.setMinSelectableDate(JDateChooser1.getDate());
        JDateChooser1.setEnabled(false);
        JDateChooser2.setEnabled(false);
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
        cardHolder.add(searchResultPanel,"SearchResult");
        cardHolder.add(old_recommendPanel,"old_recc");
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
    public void initManageTable() throws SQLException {
        System.out.println("TABLE INIT!");
        ArrayList<Order> userOrderList = new ArrayList<Order>();
        if(LoginUser.getUserName() != null){
            System.out.println("GET ORDERS FROM USER: "+LoginUser.getUserName());
            userOrderList = db.getOrderByUser(LoginUser.getUserName());
        }
        String[][] displayOrderArray = new String[userOrderList.size()][7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < userOrderList.size(); i++) {
            Order orderObj = userOrderList.get(i);
            System.out.println("PARSE ORDER DATA: "+ orderObj.getOrderNum());
            String[] row = {orderObj.getOrderNum(),orderObj.getKey(),"TITLE",sdf.format(orderObj.getStartDate()),""+orderObj.getNum(),sdf.format(orderObj.getOrderDate()),orderObj.getStatus()};
            displayOrderArray[i] = row;
        }
        String[] columns={"訂單序號","產品序號","產品名稱","出發日期","人數","下訂日期", "訂單狀態"};
        table1 = new JTable(displayOrderArray,columns);
    }

    public String getSearchField(){
        // 拿搜尋欄字串資料
        return searchField.getText();
    }

    public static void windowSizeLimiter(JFrame frame, int width, int height){
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Dimension size = frame.getSize();
                if (size.getWidth() < width) {
                    frame.setSize((int) width, (int) size.getHeight());
                }
                if (size.getHeight() < height) {
                    frame.setSize((int) size.getWidth(), (int) height);
                }
            }
        });
    }



    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(home.metalUI); // 使用Metal UI 模式啟動
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Find a Place"); // 設定視窗標題
        windowSizeLimiter(frame, 800,800);
        frame.setContentPane(new home().window);
        frame.pack();
        frame.getContentPane().requestFocusInWindow();
        frame.setSize(1000,800); // 設定初始視窗大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void createUIComponents() throws SQLException {
        // 自訂設定區域（有勾選 customCreate 的都要加在這裡，不然會產生 nullPointer 錯誤）
        loginPanel = new login().getPanel(); // loginPanel直接呼叫login.java的頁面
        aboutPanel = new about().getPanel(); // 同上
        settingsPanel = new settings().getPanel(); // 同上
        JDateChooser1 = new JDateChooser();
        JDateChooser2 = new JDateChooser();
        searchResultPanel = new JListCustomRenderer().createPanel(null);
        reccListHolder = new JListCustomRenderer().createPanel
                (db.getResult(Integer.toString(Processor.randomTravelCodeGene()),
                        0,0,"","",0,0,0));
        // TODO: 讓表格 jTable 無法被編輯，研究中。
        // public boolean isCellEditable(int row, int column){return false;}
    }
}
