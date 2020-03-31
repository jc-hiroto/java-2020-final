package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class register{
    private JLabel loginLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton btnRegister;
    private JPasswordField passwordCheckField;
    private JTextField nameField;
    private JPanel contentHolder;
    private JPanel errorAlert;

    public register() {
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(checkFieldData())
                {
                    errorAlert.setVisible(false);
                    System.out.println("Name: "+getName());
                    System.out.println("Email: "+getEmail());
                    System.out.println("Pass: "+getPassword(passwordField));
                }
                else{
                    errorAlert.setVisible(true);
                }
            }
        });
    }

    public boolean checkFieldData(){
            boolean result = true;
            if(emailValid(getEmail())){
                if(passwordValid(getPassword(passwordField),getPassword(passwordCheckField))){
                    // ACCEPT.
                    result = true;
                }
                else {
                    // INVALID: two passwords are not equal.
                    result = false;
                }
            }
            else{
                // INVALID: email field is empty or invalid.
                result = false;
            }
            return result;
    }

    public String getName(){
        return nameField.getText();
    }

    public String getEmail(){
        return emailField.getText();
    }

    public String getPassword(JPasswordField field){
        return new String(field.getPassword());
    }


    public boolean emailValid(String email){
        boolean result = true;
        if(email.isEmpty() || email.equals(null)){
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

    public boolean passwordValid(String pass1, String pass2){
        boolean result = false;
        if(pass1.isEmpty() || pass1.equals(null) || pass2.isEmpty() || pass2.equals(null)){
            result = false;
        }
        else{
            if(pass1.equals(pass2)){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    public void clearField(){
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        passwordCheckField.setText("");
    }



    public JPanel getPanel(){
        return contentHolder;
    }
}
