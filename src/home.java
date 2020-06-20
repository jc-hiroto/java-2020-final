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
    private JPanel notFoundPanel;
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
        Debugger.setDebugMode(false);
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
                System.out.println("  ________                __ __  __           \n" +
                        " /_  __/ /_  ____ _____  / /_\\ \\/ /___  __  __\n" +
                        "  / / / __ \\/ __ `/ __ \\/ //_/\\  / __ \\/ / / /\n" +
                        " / / / / / / /_/ / / / / ,<   / / /_/ / /_/ / \n" +
                        "/_/ /_/ /_/\\__,_/_/ /_/_/|_| /_/\\____/\\__,_/  \n");
                System.out.println("Group 5 - Made by 張博皓、蕭博瀚、高睿、許哲維、安彥百");
                System.exit(0);
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            // 搜尋按鈕
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
                    Debugger.showDebugMessage("[INFO] Home - Search: "+searchContent);
                    Debugger.showDebugMessage("[INFO] Home - Start Date: "+dateFormat.format(JDateChooser1.getDate()));
                    Debugger.showDebugMessage("[INFO] Home - End Date: "+ dateFormat.format(JDateChooser2.getDate()));
                    Debugger.showDebugMessage("[INFO] Home - Date Search enabled?: "+dateSearch.isSelected());
                    String code = travelCodeSearchEngine.searchTravelCode(searchContent);
                    String startDate = dateSearch.isSelected()?dateFormat.format(JDateChooser1.getDate()):"";
                    String endDate = dateSearch.isSelected()?dateFormat.format(JDateChooser2.getDate()):"";
                    int priceTop = checkBoxPrice.isSelected()?rangeSliderPrice.getHighValue():0;
                    int priceBottom = checkBoxPrice.isSelected()?rangeSliderPrice.getLowValue():0;
                    int peopleTop = checkBoxPeople.isSelected()?rangeSliderPeople.getHighValue():0;
                    int peopleBottom = checkBoxPeople.isSelected()?rangeSliderPeople.getLowValue():0;
                    Debugger.showDebugMessage("[INFO] Home - Sort mode: "+comboBox1.getSelectedIndex());
                    ArrayList<ProductData> resultList = new ArrayList<ProductData>();
                    try {
                        resultList = db.getResult(code,priceBottom,priceTop,startDate,endDate,peopleBottom,peopleTop,comboBox1.getSelectedIndex());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(resultList.size() == 0 || resultList == null){
                        layout.show(cardHolder,"NotFound");
                    }
                    else{
                        searchResultPanel = new JListCustomRenderer().createPanel(resultList);
                        cardHolder.remove(searchResultPanel);
                        cardInit();
                        layout.show(cardHolder,"SearchResult");
                    }

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
        btnManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(LoginUser.getUserName() != null){
                    cardHolder.remove(managePanel);

                    try {
                        managePanel = new manage().getPanel();
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
        cardHolder.add(notFoundPanel,"NotFound");
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

    public String getSearchField(){
        // 拿搜尋欄字串資料
        return searchField.getText();
    }

    public static void windowSizeLimiter(JFrame frame, int width, int height){
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Dimension size = frame.getSize();
                if (size.getWidth() < width || size.getHeight() < height-200) {
                    frame.setSize((int) width + 100, (int) height + 50);
                    JOptionPane.showMessageDialog(null,"過小的視窗大小會影響使用體驗! \n 建議視窗大小：1024*768","視窗大小警告",JOptionPane.WARNING_MESSAGE);
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
        frame.setContentPane(new home().window);
        frame.pack();
        frame.getContentPane().requestFocusInWindow();
        frame.setSize(1024,768); // 設定初始視窗大小
        windowSizeLimiter(frame, 1024,768);
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
    }
}
