package src;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class manage {

    private ArrayList<Order> userOrderList= new ArrayList<Order>();
    private JScrollPane tableHolder;
    private JTable table1;
    private JPanel panel;
    private JButton btnEdit;
    private JButton btnCancel;
    private int sRow = 0;


    public manage() throws SQLException {
        userOrderList = db.getOrderByUser(LoginUser.getUserName());
        btnCancel.setEnabled(false);
        btnEdit.setEnabled(false);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                sRow = table1.getSelectedRow();
                System.out.println("SELECTED: "+sRow);
                btnEdit.setEnabled(true);
                btnCancel.setEnabled(true);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(null,"您確定要刪除嗎？","刪除確認",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(check == 0){
                    String orderNum = (String) table1.getValueAt(sRow,0);
                    System.out.println("Current selected row : "+ sRow);
                    System.out.println("Current selected order number : "+userOrderList.get(sRow).getOrderNum());
                    db.deleteOrder(userOrderList.get(sRow));
                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(null,"更改人數將會取消目前訂單，並重新訂購。您確定要更改人數嗎？","更改確認",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(check == 0){
                    System.out.println("Current selected row : "+ sRow);
                    System.out.println("Current selected order number : "+table1.getValueAt(sRow,0));
                    System.out.println("Current selected people number : "+table1.getValueAt(sRow,4));
                //    db.updateOrder(userOrderList.get(sRow),);
                }
            }
        });
    }

    public void initManageTable() throws SQLException {
        System.out.println("TABLE INIT!");
        System.out.println("GET ORDERS FROM USER: "+LoginUser.getUserName());
        userOrderList = db.getOrderByUser(LoginUser.getUserName());
        String[][] displayOrderArray = new String[userOrderList.size()][7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < userOrderList.size(); i++) {
            Order orderObj = userOrderList.get(i);
            System.out.println("PARSE ORDER DATA: "+ orderObj.getOrderNum());
            String[] row = {orderObj.getOrderNum(),orderObj.getKey(),"TITLE",sdf.format(orderObj.getStartDate()),""+orderObj.getNum(),sdf.format(orderObj.getOrderDate()),orderObj.getStatus()};
            displayOrderArray[i] = row;
        }
        String[] columns={"訂單序號","產品序號","產品名稱","出發日期","人數","下訂日期", "訂單狀態"};
        table1 = new JTable(displayOrderArray,columns);
    }
    public JPanel getPanel() throws SQLException {
        //initManageTable();
        return panel;
    }
    private void createUIComponents() throws SQLException {
        if(LoginUser.getUserName() != null){
            initManageTable();
        }
        else{
            table1 = new JTable();
        }
    }
}
