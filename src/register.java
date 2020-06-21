package src;

import src.hash.passwordHash;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * manage the register page UI info
 */
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

    /**
     * default constructor of register
     */
    public register() {
        clearField();
        regSuccessAlert.setVisible(false);
        regErrorAlert.setVisible(false);
        errorAlert.setVisible(false);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            // 註冊按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(checkFieldData())
                    {
                        errorAlert.setVisible(false);
                        try {
                            registerStats = db.newUser(getName(), getEmail(), getPassword(passwordField),0);
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

    /**
     * check the field data
     * @return true if valid
     * @throws Exception
     */
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

    /**
     * get input name in the name field
     * @return
     */
    public String getName(){
        return nameField.getText();
    }

    /**
     * get input email in the email field
     * @return
     */
    public String getEmail(){
        return emailField.getText();
    }

    /**
     * get input password in the password field
     * @param field
     * @return StringBuffer encryptPass
     * @throws Exception
     */
    public StringBuffer getPassword(JPasswordField field) throws Exception {
        String passStr = new String(field.getPassword());
        StringBuffer rawPass = new StringBuffer();
        rawPass.append(passStr);
        StringBuffer encryptPass = passwordHash.encrypt(rawPass);
        return encryptPass;
    }

    /**
     * check the password is valid
     * @param pass1
     * @param pass2
     * @return true if valid
     */
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

    /**
     * clear all field including name, email, password, passwordCheck
     */
    public void clearField(){
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        passwordCheckField.setText("");
    }

    /**
     * get the register table page
     * @return JPanel contentHolder
     */
    public JPanel getPanel(){
        return contentHolder;
    }
}
