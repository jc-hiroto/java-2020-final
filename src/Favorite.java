package src;

import src.db;
import java.util.Date;

public class Favorite {

    private String favProductKey;
    private int searchCount;
    private Date lastSearchDate;

    public Favorite(){
        this.favProductKey = null;
        this.searchCount = 0;
        this.lastSearchDate = null;
    }

    public Favorite(String favProductKey, int searchCount, Date lastSearchDate){
        this.favProductKey = favProductKey;
        this.searchCount = searchCount;
        this.lastSearchDate = lastSearchDate;
    }
}