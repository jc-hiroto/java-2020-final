package src;

import javax.swing.*;
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

    public manage() throws SQLException {
        System.out.println("GET ORDERS FROM USER: "+LoginUser.getUserName());
        userOrderList = db.getOrderByUser(LoginUser.getUserName());
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
        createUIComponents();
        initManageTable();
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
