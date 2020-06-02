package src;

import java.util.Date;

public class Order {

    private String orderNumber,orderProductKey,orderStatus,orderUser;
    private int num;
    private Date startDate,orderDate;

    public Order(){
        orderNumber = null;
        orderProductKey = null;
        orderStatus = null;
        orderUser = null;
        num = 0;
        startDate = null;
        orderDate = null;
    }

    public Order(String orderNumber, String orderProductKey, String orderStatus, int num, Date startDate, Date orderDate,String orderUser) {
        this.orderNumber = orderNumber;
        this.orderProductKey = orderProductKey;
        this.orderStatus = orderStatus;
        this.orderUser = orderUser;
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

    public String getUsr(){
        return this.orderUser;
    }

    public int getNum(){
        return this.num;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public Date getOrderDate(){
        return this.orderDate;
    }
}
