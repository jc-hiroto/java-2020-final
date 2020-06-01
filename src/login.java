package src;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.db;
import src.hash.*;
import java.util.*;

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
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(loginCardHolder, "Register");
                btnBack.setVisible(true);
                btnRegister.setVisible(false);
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
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
                            System.out.println("Login success, username: "+LoginUser.getUserName());
                            db.deleteOrder("test","ORD4");
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
            public void actionPerformed(ActionEvent actionEvent) {
                clearField();
                setLoginStatus(false);
                layout.show(loginCardHolder,"Login");
                btnRegister.setVisible(true);
                btnLogin.setVisible(true);
                loadingBar.setVisible(false);
                System.out.println("[Operation] User logout.");
                LoginUser.setUserName(null);
            }
        });
        btnBackLogin.addActionListener(new ActionListener() {
            @Override
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

    public static boolean getLoginStatus(){
        return loginstatus;
    }
    public static void setLoginStatus(boolean set){
        loginstatus = set;
    }
    public static void setUserName(String name){
        userName = name;
    }
    public static String getUserName(){
        if(userName.equals("ERR") || !getLoginStatus()){
            return "";
        }
        else{
            return userName;
        }
    }
    public String getEmail(){
        return emailField.getText();
    }
    public StringBuffer getPassword() throws Exception {
        String passStr = new String(passwordField.getPassword());
        System.out.println("ORIPASS: "+passStr);
        StringBuffer rawPass = new StringBuffer();
        rawPass.append(passStr);
        StringBuffer encryptPass = passwordHash.encrypt(rawPass);
        return encryptPass;
    }


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
    public void clearField(){
        emailField.setText("");
        passwordField.setText("");
    }
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

    public JPanel getPanel(){
        return contentHolder;
    }

    private void createUIComponents() {
        registerPanel = new register().getPanel();
    }
}
