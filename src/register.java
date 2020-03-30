package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class register {
    private JLabel loginLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton btnRegister;
    private JPasswordField passwordCheckField;
    private JTextField nameField;
    private JPanel contentHolder;

    public register() {
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    public JPanel getPanel(){
        return contentHolder;
    }
    private void createUIComponents() {

    }
}
