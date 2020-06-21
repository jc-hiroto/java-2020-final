package src;

import java.util.Date;

/**
 * data structure of order
 */
public class Order {

    private String orderNumber,orderProductKey,orderStatus,orderUser,orderTitle;
    private int num;
    private Date startDate,orderDate;

    /**
     * default constructor of Order
     */
    public Order(){
        orderNumber = null;
        orderProductKey = null;
        orderStatus = null;
        orderUser = null;
        num = 0;
        startDate = null;
        orderDate = null;
        orderTitle = null;
    }

    /**
     * constructor of Order
     * @param orderNumber
     * @param orderProductKey
     * @param orderStatus
     * @param orderUser
     * @param num
     * @param startDate
     * @param orderDate
     * @param orderTitle
     */
    public Order(String orderNumber, String orderProductKey, String orderStatus, String orderUser, int num, Date startDate, Date orderDate, String orderTitle) {
        this.orderNumber = orderNumber;
        this.orderProductKey = orderProductKey;
        this.orderStatus = orderStatus;
        this.orderUser = orderUser;
        this.num = num;
        this.startDate = startDate;
        this.orderDate = orderDate;
        this.orderTitle = orderTitle;
    }

    /**
     * get method of elements in Order
     * @return elements in Order
     */
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

    public String getOrderTitle(){return this.orderTitle;}

}
