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

    public login() {
        errorAlert.setVisible(false);
        loginCardHolder.add(loginPanel, "Login");
        loginCardHolder.add(registerPanel, "Register");
        loginCardHolder.add(successPanel, "Success");
        loginCardHolder.add(errorPanel, "Error");
        layout = (CardLayout)loginCardHolder.getLayout();
        if(!getLoginStatus()){
            layout.show(loginCardHolder, "Login");
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
                emailField.setText("");
                passwordField.setText("");
                errorAlert.setVisible(false);
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    if(emailValid(getEmail()) && passwordValid(getPassword()))
                    {
                        errorAlert.setVisible(false);
                        loadingBar.setVisible(true);
                        btnLogin.setVisible(false);
                        btnRegister.setVisible(false);
                        if(true){
                            // TODO: ADD verification method to check login status.
                            layout.show(loginCardHolder, "Success");
                            System.out.println("Email: "+getEmail());
                            System.out.println("Pass: "+getPassword());
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
                setLoginStatus(false);
                layout.show(loginCardHolder,"Login");
                btnRegister.setVisible(true);
                btnLogin.setVisible(true);
                loadingBar.setVisible(false);
            }
        });
        btnBackLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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

    public String getEmail(){
        return emailField.getText();
    }
    public StringBuffer getPassword() throws Exception {
        String passStr = new String(passwordField.getPassword());
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
