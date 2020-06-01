package src;
import src.ProductCombination;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

public class ProductData {
    private String title,key,code;
    public ArrayList<ProductCombination> detail;

    public ProductData(){
        title = null;
        key = null;
        code = null;
        detail = new ArrayList<ProductCombination>();
    }

    public ProductData(String title,String key,String code){
        this.title = title;
        this.key = key;
        this.code = code;
        detail = new ArrayList<ProductCombination>();
    }

    public void addCombination(ProductCombination comb){
        detail.add(comb);
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setKey(String key){
        this.key = key;
    }
    public void setCode(String code){
        this.code = code;
    }

    @Override
    public String toString(){
        return new String(title);
    }
    public String getTitle(){
        return this.title;
    }
    public String getKey(){
        return this.key;
    }
    public String getCode(){
        return this.code;
    }
    public int getCombinationSize(){
        return detail.size();
    }
    public ArrayList<ProductCombination> getCombination(){
        return detail;
    }
    public int getCheapestPrice(){
        int min = detail.get(0).getPrice();
        for(int i=0; i<detail.size(); i++){
           if(detail.get(i).getPrice() < min) {
               min = detail.get(i).getPrice();
           }
        }
        return min;
    }
    public int getLowestBound(){
        int min = detail.get(0).getLowerBound();
        for(int i=0; i<detail.size(); i++){
            if(detail.get(i).getLowerBound() < min) {
                min = detail.get(i).getLowerBound();
            }
        }
        return min;
    }

}
