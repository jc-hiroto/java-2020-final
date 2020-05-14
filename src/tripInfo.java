package src;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import src.ProductData;
import src.ProductCombination;
import src.hash.searchEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static src.Processor.textCutter;

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

    public tripInfo(ProductData PD){
        PDHold = PD;
        PCArrayL = PD.getCombination();
        orderPanel.setVisible(false);
        orderPeople.setValue(1);
        String imgFilepath=System.getProperty("user.dir")+ "/img/trip/"+PD.getCode()+"-1x.png";
        lbIcon.setIcon(new ImageIcon(imgFilepath));
        lbTitle.setText(""+PD.getTitle());
        lbKey.setText("產品編號："+PD.getKey());
        lbCat.setText("分類："+ new searchEngine().reverseSearch(PD.getCode()));
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                orderPanel.setVisible(true);
            }
        });
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int sRow = table1.getSelectedRow();
                //System.out.println("[INFO] Selected row: "+ sRow);
                System.out.println("[INFO] Selected trip product key: "+ PDHold.getKey());
                System.out.println("[INFO] Selected row Starting date: "+ table1.getValueAt(sRow,0));
                System.out.println("[INFO] Order people amount: "+ orderPeople.getValue());
            }
        });
    }
    public JPanel getPanel(){
        return tripInfoPanel;
    }
    public void initTable(){
        PCArrayL = PDHold.getCombination();
        String[][] displayPCArray = new String[PCArrayL.size()][6];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < PCArrayL.size(); i++) {
            ProductCombination PCObj = PCArrayL.get(i);
            String[] row = {sdf.format(PCObj.getStartDate()),sdf.format(PCObj.getEndDate()),""+PCObj.getLowerBound(),""+PCObj.getUpperBound(),""+PCObj.getPrice(),"OK"};
            displayPCArray[i] = row;
        }
        String[] columns={"出團日期","返回日期","最低出團人數","最高出團人數","價格", "產品狀態"};
        table1 = new JTable(displayPCArray,columns);
    }

    private void createUIComponents() {
        initTable();
        orderPeople = new JSpinner(new SpinnerNumberModel(1.0, 1.0, 12.0, 1.0));
    }
}

