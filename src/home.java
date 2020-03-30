package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home {
    private JPanel panel1;
    private JButton btnSearch;
    private JTextField searchField;
    private JButton btnRecommand;
    private JButton btnStar;
    private JButton btnList;
    private JButton btnExit;
    private JButton btnSettings;
    private JButton btnInfo;
    private JButton btnLogin;
<<<<<<< HEAD
    private JPanel Home;
    private JPanel bottom;
    private JPanel homeContentHolder;
    private JPanel loginPanel;
    private JPanel homePanel;
    private JPanel cardHolder;
    private JPanel aboutPanel;
    private JButton btnHome;
    private JButton btnAbout;
    private JPanel registerPanel;
=======
>>>>>>> parent of f6adda2... 20200330: GUI update.
    public static String metalUI = "javax.swing.plaf.metal.MetalLookAndFeel";
    public CardLayout layout = null;

    public home() {
<<<<<<< HEAD
        cardInit();
        btnHome.setVisible(false);
=======

>>>>>>> parent of f6adda2... 20200330: GUI update.
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String searchContent = getSearchField();
                System.out.println("Search: "+searchContent);
            }
        });
        btnRecommand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        btnStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
<<<<<<< HEAD
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent){
                layout.show(cardHolder, "Login");
                exitFromHome();
            }
        });
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                backToHome();
                btnLogin.setVisible(true);
            }
        });
        btnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layout.show(cardHolder,"About");
                exitFromHome();
            }
        });
    }
    public void goToRegister(){
        layout.show(cardHolder,"Register");
        exitFromHome();
    }

    public void cardInit(){
        cardHolder.add(homePanel, "Home");
        cardHolder.add(loginPanel, "Login");
        cardHolder.add(registerPanel, "Register");
        cardHolder.add(aboutPanel, "About");

        layout = (CardLayout)cardHolder.getLayout();
    }

    public void backToHome(){
        layout.show(cardHolder, "Home");
        btnHome.setVisible(false);
        btnAbout.setVisible(true);
        btnSettings.setVisible(true);
    }

    public void exitFromHome(){
        btnHome.setVisible(true);
        btnLogin.setVisible(false);
        btnSettings.setVisible(false);
        btnAbout.setVisible(false);
=======
>>>>>>> parent of f6adda2... 20200330: GUI update.
    }

    public String getSearchField(){
        return searchField.getText();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(home.metalUI);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Home");
<<<<<<< HEAD
        frame.setSize(1000,800);
        frame.setContentPane(new home().window);
=======
        frame.setContentPane(new home().panel1);
>>>>>>> parent of f6adda2... 20200330: GUI update.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
