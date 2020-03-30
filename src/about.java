package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class about {
    private JPanel contentHolder;
    private JButton visitGitHubPageButton;

    public about() {
        visitGitHubPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Runtime.getRuntime().exec(new String[] {"/usr/bin/open", "-a", "/Applications/Google Chrome.app", "https://github.com/jc-hiroto/java-2020-final"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JPanel getPanel(){
        return contentHolder;
    }

    private void createUIComponents() {
    }
}
