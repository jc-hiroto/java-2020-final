package src;

import javax.swing.*;
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
    private JButton button1;

    public login() {
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    public JPanel getPanel(){
        return contentHolder;
    }
}
