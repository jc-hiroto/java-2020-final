package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * hyperlink setting to open GitHub source code
 */
public class about {
    private JPanel contentHolder;
    private JButton visitGitHubPageButton;

    /**
     * default constructor of GitHub Link button
     */
    public about() {
        visitGitHubPageButton.addActionListener(new ActionListener() {
            @Override
            // github 按鈕超連結，以chrome開啟。
            // TODO: 目前僅macOS可用，設定多系統通用
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Runtime.getRuntime().exec(new String[] {"/usr/bin/open", "-a", "/Applications/Google Chrome.app", "https://github.com/jc-hiroto/java-2020-final"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * get relevant showing window
     * abstract class
     * @return contentHolder
     */
    public JPanel getPanel(){
        return contentHolder;
    }
}
