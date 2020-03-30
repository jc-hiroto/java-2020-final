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
    private JButton btnRegister;
    private JPanel loginCardHolder;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JButton btnBack;

    public login() {
        loginCardHolder.add(loginPanel, "Login");
        loginCardHolder.add(registerPanel, "Register");
        CardLayout layout = (CardLayout)loginCardHolder.getLayout();
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(loginCardHolder, "Register");
                btnBack.setVisible(true);

            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(loginCardHolder, "Login");
                btnBack.setVisible(false);
            }
        });
    }

    public JPanel getPanel(){
        return contentHolder;
    }

    private void createUIComponents() {
        registerPanel = new register().getPanel();
    }
}
