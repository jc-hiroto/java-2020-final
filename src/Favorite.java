package src;

import java.util.Date;

public class Favorite {

    private String favUser;
    private String favProductKey;
    private int searchCount;
    private Date lastSearchDate;

    public Favorite(){
        this.favUser = favUser;
        this.favProductKey = null;
        this.searchCount = 0;
        this.lastSearchDate = null;
    }

    public Favorite(String favUser, String favProductKey, int searchCount, Date lastSearchDate){
        this.favUser = favUser;
        this.favProductKey = favProductKey;
        this.searchCount = searchCount;
        this.lastSearchDate = lastSearchDate;
    }

    public String getUsr(){
        return this.favUser;
    }
    public String getKey(){
        return new String(this.favProductKey);
    }
    public int getCount(){
        return this.searchCount;
    }
    public Date getLastSearchDate(){
        return this.lastSearchDate;
    }
}