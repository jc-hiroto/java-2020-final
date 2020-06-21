package src;

import java.util.Date;

/**
 * Data structure of ProductCombination
 */
public class ProductCombination {

    private int price,upperBound,lowerBound,currentOrder;
    private Date startDate,endDate;

    /**
     * default constructor of ProductCombination
     */
    public ProductCombination(){
        price = 0;
        upperBound = 0;
        lowerBound = 0;
        currentOrder = 0;
        startDate = null;
        endDate = null;
    }

    /**
     * constructor of ProductCombination
     * @param pri
     * @param up
     * @param low
     * @param curOrder
     * @param start
     * @param end
     */
    public ProductCombination(int pri,int up,int low,int curOrder,Date start,Date end){
        price = pri;
        upperBound = up;
        lowerBound = low;
        currentOrder = curOrder;
        startDate = start;
        endDate = end;
    }

    /**
     * set new ProductCombination
     * @param old
     */
    public ProductCombination(ProductCombination old){
        price = old.price;
        upperBound = old.upperBound;
        lowerBound = old.lowerBound;
        currentOrder = old.currentOrder;
        startDate = old.startDate;
        endDate = old.endDate;
    }

    /**
     * set methods of all elements
     */
    public void setPrice(int price) {
        this.price = price;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setCurrentOrder(int or){ this.currentOrder = or; };
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * get methods of all elements
     */
    public int getPrice() {
        return price;
    }
    public int getLowerBound() {
        return lowerBound;
    }
    public int getUpperBound() {
        return upperBound;
    }
    public int getCurrentOrder(){ return currentOrder ;}
    public Date getStartDate(){
        return startDate;
    }
    public Date getEndDate(){
        return endDate;
    }
}
