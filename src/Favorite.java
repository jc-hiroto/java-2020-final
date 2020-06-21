package src;

import java.util.Date;

/**
 * define Favorite data structure
 */
public class Favorite {

    private String favUser;
    private String favProductKey;
    private int searchCount;
    private Date lastSearchDate;

    /**
     * default constructor of Favorite
     */
    public Favorite(){
        this.favUser = favUser;
        this.favProductKey = null;
        this.searchCount = 0;
        this.lastSearchDate = null;
    }

    /**
     * constructor of Favorite
     * @param favUser
     * @param favProductKey
     * @param searchCount
     * @param lastSearchDate
     */
    public Favorite(String favUser, String favProductKey, int searchCount, Date lastSearchDate){
        this.favUser = favUser;
        this.favProductKey = favProductKey;
        this.searchCount = searchCount;
        this.lastSearchDate = lastSearchDate;
    }

    /**
     * get method of elements in Favorite
     * @return element respectively
     */
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