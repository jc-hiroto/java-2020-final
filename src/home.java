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
    public static String metalUI = "javax.swing.plaf.metal.MetalLookAndFeel";

    public home() {

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
        frame.setContentPane(new home().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
