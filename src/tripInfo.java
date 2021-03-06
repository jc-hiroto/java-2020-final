package src;

import src.hash.searchEngine;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static src.Processor.textCutter;

/**
 * manage the trip info page
 */
public class tripInfo {
    private JPanel tripInfoPanel;
    private JLabel lbTitle;
    private JLabel lbIcon;
    private JLabel lbKey;
    private JLabel lbCat;
    private JTable table1;
    private JButton btnOrder;
    private JSpinner orderPeople;
    private JPanel orderPanel;
    private ProductData PDHold;
    private ArrayList<ProductCombination> PCArrayL;

    /**
     * constructor of the trip info
     * @param PD
     */
    public tripInfo(ProductData PD){
        PDHold = PD;
        PCArrayL = PD.getCombination();
        btnOrder.setEnabled(false);
        orderPeople.setEnabled(false);
        orderPeople.setValue(1);
        URL url = this.getClass().getResource(
                "/img/trip/"+PD.getCode()+"-1x.png");
        ImageIcon icon = new ImageIcon(url);
        lbIcon.setIcon(icon);
        lbTitle.setText(textCutter(""+PD.getTitle(),750,lbTitle.getFontMetrics(lbTitle.getFont())));
        lbKey.setText("產品編號："+PD.getKey());
        lbCat.setText("分類："+ new searchEngine().reverseSearch(PD.getCode()));
        JDialog.setDefaultLookAndFeelDecorated(true);

        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if(LoginUser.getUserName() != null){
                    btnOrder.setEnabled(true);
                    orderPeople.setEnabled(true);
                }
                else {
                    JOptionPane.showMessageDialog(null,"請登入後再下訂！","您尚未登入！",JOptionPane.WARNING_MESSAGE);
                }
                if(Integer.valueOf((String)table1.getValueAt(table1.getSelectedRow(),5))==0){
                    btnOrder.setEnabled(false);
                    orderPeople.setEnabled(false);
                }
            }
        });
        btnOrder.addActionListener(new ActionListener() {
            @Override
            // 下訂按鈕
            public void actionPerformed(ActionEvent actionEvent) {
                int sRow = table1.getSelectedRow();
                Date startDate = new Date();
                Date endDate = new Date();
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    startDate = sdf.parse((String) table1.getValueAt(sRow,0));
                    endDate = sdf.parse((String) table1.getValueAt(sRow,1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int check = JOptionPane.showConfirmDialog(null,"您確定要下訂了嗎？","下訂確認",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(check == 0){
                    Debugger.showDebugMessage("[INFO] TripInfoPanel - Selected row"+sRow);
                    Debugger.showDebugMessage("[INFO] TripInfoPanel - Selected trip product key: "+ PDHold.getKey());
                    Debugger.showDebugMessage("[INFO] TripInfoPanel - Selected row Starting date: "+ startDate.toString());
                    Debugger.showDebugMessage("[INFO] TripInfoPanel - Selected row End date: "+ endDate.toString());
                    Debugger.showDebugMessage("[INFO] TripInfoPanel - Order people amount: "+ orderPeople.getValue());
                    Debugger.showDebugMessage("[INFO] TripInfoPanel - Order user: "+ LoginUser.getUserName());
                    int people = (int) orderPeople.getValue();
                    int condition = db.newOrder(LoginUser.getUserName(),PDHold,PDHold.getCombByStartDate(startDate), people);
                    switch (condition){
                        case 0:
                            JOptionPane.showMessageDialog(null,"下訂成功，祝您有個美好的旅程！","下訂成功",JOptionPane.INFORMATION_MESSAGE);
                            table1.setValueAt(Integer.toString(Integer.valueOf(table1.getValueAt(sRow,5).toString()) - people) , sRow,5);
                            break;
                        case -1:
                            JOptionPane.showMessageDialog(null,"空位不足","下訂失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                        case -2:
                            JOptionPane.showMessageDialog(null,"系統錯誤","下訂失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,"未知錯誤","下訂失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    Debugger.showDebugMessage("[INFO] TripInfoPanel - New order status: "+condition);
                }
            }
        });
    }

    /**
     * get the trip info page
     * @return JPanel tripInfoPanel
     */
    public JPanel getPanel(){
        return tripInfoPanel;
    }

    /**
     * initialize the trip info table
     */
    public void initTable(){
        PCArrayL = PDHold.getCombination();
        String[][] displayPCArray = new String[PCArrayL.size()][6];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < PCArrayL.size(); i++) {
            ProductCombination PCObj = PCArrayL.get(i);
            String[] row = {sdf.format(PCObj.getStartDate()),sdf.format(PCObj.getEndDate()),""+PCObj.getLowerBound(),""+PCObj.getUpperBound(),""+PCObj.getPrice(),""+(PCObj.getUpperBound()-PCObj.getCurrentOrder())};
            displayPCArray[i] = row;
        }
        String[] columns={"出團日期","返回日期","最低出團人數","最高出團人數","價格", "剩餘可報人數"};
        table1 = new JTable(displayPCArray,columns);
    }

    /**
     * create trip info UI components
     */
    private void createUIComponents() {
        initTable();
        orderPeople = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
    }
}

