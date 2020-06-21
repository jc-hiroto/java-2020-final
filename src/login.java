package src;

import src.hash.passwordHash;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * define the interface of login page UI components
 * @param <loginStatus>
 */
public class login<loginStatus> {
    private JPanel contentHolder;
    private JLabel loginLabel;
    private JLabel userNameDisplay;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private JButton btnRegister;
    private JPanel loginCardHolder;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JButton btnBack;
    private JPanel errorAlert;
    private JProgressBar loadingBar;
    private JPanel successPanel;
    private JButton btnLogout;
    private JPanel errorPanel;
    private JButton btnBackLogin;
    private JPanel loadingPanel;
    private static boolean loginstatus = false;
    private CardLayout layout = null;
    private static String userName = "";

    /**
     * default constructor of login
     */
    public login() {
        errorAlert.setVisible(false);
        loginCardHolder.add(loginPanel, "Login");
        loginCardHolder.add(registerPanel, "Register");
        loginCardHolder.add(successPanel, "Success");
        loginCardHolder.add(errorPanel, "Error");
        layout = (CardLayout)loginCardHolder.getLayout();
        if(!getLoginStatus()){
            layout.show(loginCardHolder, "Login");
            clearField();
        }
        else{
            layout.show(loginCardHolder, "Success");
            userNameDisplay.setText(userName);
        }
        btnRegister.addActionListener(new ActionListener() {
            @Override
            // 註冊按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(loginCardHolder, "Register");
                btnBack.setVisible(true);
                btnRegister.setVisible(false);
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            // 返回按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(loginCardHolder, "Login");
                btnBack.setVisible(false);
                btnRegister.setVisible(true);
                clearField();
                errorAlert.setVisible(false);
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            // 登入按鈕
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    if(src.Processor.mailAddressValidChecker(getEmail()) && passwordValid(getPassword()))
                    {
                        errorAlert.setVisible(false);
                        loadingBar.setVisible(true);
                        btnLogin.setVisible(false);
                        btnRegister.setVisible(false);
                        userName = db.userAuth(getEmail(), getPassword());
                        if(userName != "ERR"){
                            userNameDisplay.setText(userName);
                            layout.show(loginCardHolder, "Success");
                            LoginUser.setUserName(userName);
                            Debugger.showDebugMessage("[SUCCESS] LoginPanel - Login username: "+LoginUser.getUserName());
                            setLoginStatus(true);
                        }
                        else{
                            layout.show(loginCardHolder,"Error");
                            btnRegister.setVisible(true);
                            setLoginStatus(false);
                        }
                    }
                    else{
                        errorAlert.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            // 登出按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                clearField();
                setLoginStatus(false);
                layout.show(loginCardHolder,"Login");
                btnRegister.setVisible(true);
                btnLogin.setVisible(true);
                loadingBar.setVisible(false);
                Debugger.showDebugMessage("[SUCCESS] LoginPanel -  User logout.");
                LoginUser.setUserName(null);
            }
        });
        btnBackLogin.addActionListener(new ActionListener() {
            @Override
            // 返回登入按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                emailField.setText("");
                passwordField.setText("");
                layout.show(loginCardHolder,"Login");
                btnRegister.setVisible(true);
                btnLogin.setVisible(true);
                loadingBar.setVisible(false);
            }
        });
    }

    // get the login status
    // true if logged in
    public static boolean getLoginStatus(){
        return loginstatus;
    }

    // set log in status
    // true if logged in
    public static void setLoginStatus(boolean set){
        loginstatus = set;
    }

    // set user name
    public static void setUserName(String name){
        userName = name;
    }

    // get user name
    public static String getUserName(){
        if(userName.equals("ERR") || !getLoginStatus()){
            return "";
        }
        else{
            return userName;
        }
    }

    // get email info from email field
    public String getEmail(){
        return emailField.getText();
    }

    // get password from password field
    public StringBuffer getPassword() throws Exception {
        String passStr = new String(passwordField.getPassword());
        StringBuffer rawPass = new StringBuffer();
        rawPass.append(passStr);
        StringBuffer encryptPass = passwordHash.encrypt(rawPass);
        return encryptPass;
    }

    // check if the email is valid
    // true if valid
    public boolean emailValid(String email){
        boolean result = true;
        if(email.isEmpty() || email == null){
            result = false;
        }
        else if(!email.contains("@")){
            result = false;
        }
        else{
            result = true;
        }
        return result;
    }

    // clear the email, password field
    public void clearField(){
        emailField.setText("");
        passwordField.setText("");
    }

    // check if the password is valid
    // true if valid
    public boolean passwordValid(StringBuffer pass){
        boolean result = true;
        if(pass.toString().equals("ERR")){
            result = false;
        }
        else{
            result = true;
        }
        return result;
    }

    // get the Java panel
    public JPanel getPanel(){
        return contentHolder;
    }

    // create log in UI components
    private void createUIComponents() {
        registerPanel = new register().getPanel();
    }
}
