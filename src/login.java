package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    private JPanel contentHolder;
    private JLabel loginLabel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnLogin;
    private JButton btnForgot;
    private JButton btnRegister;
    private JPanel loginHolder;
    private JPanel loginPanel;
    private JPanel registerPanel;

    public login() {
        loginHolder.add(loginPanel, "Login");
        loginHolder.add(registerPanel, "Register");
        CardLayout loginLayout = (CardLayout)loginHolder.getLayout();
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginLayout.show(loginHolder,"Register");
            }
        });

    }

    public JPanel getPanel(){
        return contentHolder;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
