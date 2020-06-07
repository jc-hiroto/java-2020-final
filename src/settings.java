package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class settings {
    private JPanel contentHolder;
    private JCheckBox debugModeCheckBox;
    private JPanel caution;
    private JButton iUnderstandTheConsequencesButton;

    public settings() {
        caution.setVisible(false);
        debugModeCheckBox.addActionListener(new ActionListener() {
            @Override
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
            public void actionPerformed(ActionEvent actionEvent) {
                Debugger.setDebugMode(true);
                caution.setVisible(false);
            }
        });
    }

    public JPanel getPanel(){
        return contentHolder;
    }
}
