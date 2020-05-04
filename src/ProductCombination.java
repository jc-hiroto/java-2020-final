package src;

import java.util.Date;

public class ProductCombination {

    public int price,upperBound,lowerBound;
    public Date startDate,endDate;

    public ProductCombination(){
        price = 0;
        upperBound = 0;
        lowerBound = 0;
        startDate = null;
        endDate = null;
    }

    public ProductCombination(int pri,int up,int low,Date start,Date end){
        price = pri;
        upperBound = up;
        lowerBound = low;
        startDate = start;
        endDate = end;
    }

    public ProductCombination(ProductCombination old){
        price = old.price;
        upperBound = old.upperBound;
        lowerBound = old.lowerBound;
        startDate = old.startDate;
        endDate = old.endDate;
    }
}
