package src;

import src.hash.passwordHash;

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
    private JPanel regSuccessAlert;
    private JPanel regErrorAlert;
    private static boolean registerStats = false;

    public register() {
        clearField();
        regSuccessAlert.setVisible(false);
        regErrorAlert.setVisible(false);
        errorAlert.setVisible(false);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(checkFieldData())
                    {
                        errorAlert.setVisible(false);
                        try {
                            registerStats = db.newUser(getName(), getEmail(), getPassword(passwordField));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(registerStats){
                            regSuccessAlert.setVisible(true);
                            regErrorAlert.setVisible(false);
                        }
                        else{
                            regErrorAlert.setVisible(true);
                            regSuccessAlert.setVisible(false);
                        }
                    }
                    else{
                        errorAlert.setVisible(true);
                    }
                    clearField();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean checkFieldData() throws Exception {
            boolean result = true;
            if(Processor.mailAddressValidChecker(getEmail())){
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

    public StringBuffer getPassword(JPasswordField field) throws Exception {
        String passStr = new String(field.getPassword());
        StringBuffer rawPass = new StringBuffer();
        rawPass.append(passStr);
        StringBuffer encryptPass = passwordHash.encrypt(rawPass);
        return encryptPass;
    }

    public boolean passwordValid(StringBuffer pass1, StringBuffer pass2){
        boolean result = false;
        if(pass1.toString().equals("ERR") || pass2.toString().equals("ERR")){
            result = false;
        }
        else{
            if(pass1.toString().equals(pass2.toString())){
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
