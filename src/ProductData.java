package src;
import src.*;

import java.util.ArrayList;

public class ProductData {
    public String title,key,code;
    public ArrayList<src.ProductCombination> detail;

    public ProductData(){
        title = null;
        key = null;
        code = null;
        detail = null;
    }

    public ProductData(String getTitle,String getKey,String getCode){
        title = getTitle;
        key = getKey;
        code = getCode;
        detail = new ArrayList<src.ProductCombination>();
    }
}
