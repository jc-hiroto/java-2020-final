package src;

import java.util.Date;
import src.db;

public class Order {

    private String orderNumber,orderProductKey,orderStatus;
    private int num;
    private Date startDate,orderDate;

    public Order(){
        orderNumber = null;
        orderProductKey = null;
        orderStatus = null;
        num = 0;
        startDate = null;
        orderDate = null;
    }

    public Order(String orderNumber, String orderProductKey, String orderStatus, int num, Date startDate, Date orderDate) {
        this.orderNumber = orderNumber;
        this.orderProductKey = orderProductKey;
        this.orderStatus = orderStatus;
        this.num = num;
        this.startDate = startDate;
        this.orderDate = orderDate;
    }

    public String getOrderNum(){
        return new String(this.orderNumber);
    }

    public String getKey(){
        return new String(this.orderProductKey);
    }

    public String getStatus(){
        return new String(this.orderStatus);
    }

    public int getNum(){
        return this.num;
    }
}
