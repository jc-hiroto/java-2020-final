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
    private JPanel editPanel;
    private JSpinner editPeople;
    private JButton confirm;
    private int sRow = 0;


    public manage() throws SQLException {
        editPanel.setVisible(false);
        userOrderList = db.getOrderByUser(LoginUser.getUserName());
        btnCancel.setEnabled(false);
        btnEdit.setEnabled(false);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                sRow = table1.getSelectedRow();
                if(table1.getValueAt(sRow,6).equals("訂單已取消")){
                    btnEdit.setEnabled(false);
                    btnCancel.setEnabled(false);
                }
                else{
                    btnEdit.setEnabled(true);
                    btnCancel.setEnabled(true);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(null,"您確定要取消該筆訂單嗎？","取消確認",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(check == 0){
                    String orderNum = (String) table1.getValueAt(sRow,0);
                    System.out.println("[INFO] Current selected row : "+ sRow);
                    System.out.println("[INFO] Current selected order number : "+userOrderList.get(sRow).getOrderNum());
                    boolean stats = db.deleteOrder(userOrderList.get(sRow));
                    if(stats){
                        btnEdit.setEnabled(false);
                        btnCancel.setEnabled(false);
                        table1.setValueAt("訂單已取消",sRow,6);
                        JOptionPane.showMessageDialog(null,"該筆訂單已取消，期待你再次訂購！","操作成功",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"系統錯誤：訂單取消失敗，請聯絡管理員。","操作失敗",JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(null,"更改人數將會取消目前訂單，並重新訂購。您確定要更改人數嗎？","更改確認",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(check == 0){
                    System.out.println("[INFO] Current selected row : "+ sRow);
                    System.out.println("[INFO] Current selected order number : "+table1.getValueAt(sRow,0));
                    System.out.println("[INFO] Current selected people number : "+table1.getValueAt(sRow,4));
                    editPanel.setVisible(true);
                }
            }
        });
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int newPeople = (int) editPeople.getValue();
                int check = JOptionPane.showConfirmDialog(null,"確定更改人數成: "+newPeople+" 人嗎？","更改確認",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(check == 0 ){
                    int updateStats = db.updateOrder(userOrderList.get(sRow),newPeople);
                    switch (updateStats) {
                        case 0:
                            JOptionPane.showMessageDialog(null,"該筆訂單人數已成功更改，請重新進入訂單管理確認。","操作成功",JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case -1:
                            JOptionPane.showMessageDialog(null,"更改後人數超過可訂人數，請重新選擇人數。","操作失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                        case -2:
                            JOptionPane.showMessageDialog(null,"系統錯誤：訂單更改失敗，請聯絡管理員。（ERR02: DB_SQL_ERROR）","操作失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                        case -3:
                            JOptionPane.showMessageDialog(null,"系統錯誤：訂單更改失敗，請聯絡管理員。（ERR03: UPDATE_AMOUNTDATA_ERROR）","操作失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                        case -4:
                            JOptionPane.showMessageDialog(null,"系統錯誤：訂單更改失敗，請聯絡管理員。（ERR04: READD_ORDER_ERROR）","操作失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                        case -5:
                            JOptionPane.showMessageDialog(null,"系統錯誤：訂單更改失敗，請聯絡管理員。（ERR05: DEL_ORDER_ERROR）","操作失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,"系統錯誤：訂單更改失敗，請聯絡管理員。（UNKNOWN ERROR）","操作失敗",JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    editPanel.setVisible(false);
                }
            }
        });
    }

    public void initManageTable() throws SQLException {
        System.out.println("[SUCCESS] TABLE INITIATED!");
        System.out.println("[INFO] GET ORDERS FROM USER: "+LoginUser.getUserName());
        userOrderList = db.getOrderByUser(LoginUser.getUserName());
        String[][] displayOrderArray = new String[userOrderList.size()][7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < userOrderList.size(); i++) {
            Order orderObj = userOrderList.get(i);
            String displayStats  = "";
            switch (orderObj.getStatus()){
                case "OKAY":
                    displayStats = "訂單成立";
                    break;
                case "YET":
                    displayStats = "未達出團人數";
                    break;
                case "CANCELED":
                    displayStats = "訂單已取消";
                    break;
                default:
                    displayStats = orderObj.getStatus();
                    break;
            }
            System.out.println("[INFO] PARSE ORDER DATA: "+ orderObj.getOrderNum());
            String[] row = {orderObj.getOrderNum(),orderObj.getKey(), db.getTitleByKey(orderObj.getKey()),sdf.format(orderObj.getStartDate()),""+orderObj.getNum(),sdf.format(orderObj.getOrderDate()),displayStats};
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
        editPeople = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
    }
}
