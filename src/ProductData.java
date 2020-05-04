package src;
import src.ProductCombination;

import java.security.SecureRandom;
import java.util.ArrayList;

public class ProductData {
    private String title,key,code;
    private ArrayList<ProductCombination> detail;

    public ProductData(){
        title = null;
        key = null;
        code = null;
        detail = new ArrayList<ProductCombination>();
    }

    public ProductData(String getTitle,String getKey,String getCode){
        title = getTitle;
        key = getKey;
        code = getCode;
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
        return new String(this.title);
    }
    public String getKey(){
        return new String(this.key);
    }
    public String getCode(){
        return new String(this.code);
    }
    public int getCombinationSize(){
        return detail.size();
    }
    public ArrayList<ProductCombination> getCombination(){
        return detail;
    }
    public ProductCombination getFirstCombination(){
        return new ProductCombination(detail.get(0));
    }
}