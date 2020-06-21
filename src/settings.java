package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * manage the setting page
 */
public class settings {
    private JPanel contentHolder;
    private JCheckBox debugModeCheckBox;
    private JPanel caution;
    private JButton iUnderstandTheConsequencesButton;

    /**
     * default constructor of settings
     */
    public settings() {
        caution.setVisible(false);
        debugModeCheckBox.addActionListener(new ActionListener() {
            @Override
            // 除錯模式勾選
            public void actionPerformed(ActionEvent actionEvent) {
                if(debugModeCheckBox.isSelected()){
                    caution.setVisible(true);
                }
                else {
                    caution.setVisible(false);
                    Debugger.setDebugMode(false);
                }
            }
        });
        iUnderstandTheConsequencesButton.addActionListener(new ActionListener() {
            @Override
            // 我了解後果按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                Debugger.setDebugMode(true);
                caution.setVisible(false);
            }
        });
    }

    /**
     * get the setting table page
     * @return JPanel contentHolder
     */
    public JPanel getPanel(){
        return contentHolder;
    }
}
