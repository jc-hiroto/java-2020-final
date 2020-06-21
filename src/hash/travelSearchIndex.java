package src.hash;

import src.Debugger;

import java.util.ArrayList;

/**
 * define travel code index
 * define travel code
 * define travel code index
 */
public class travelSearchIndex {
    private ArrayList<String> travelCode;
    private ArrayList<String> travelCodeName;

    /**
     * default constructor of travel search index
     * init travel code
     * init travel code name
     */
    travelSearchIndex(){
        travelCode = new ArrayList<String>();
        travelCodeName = new ArrayList<String>();
    }

    /**
     * add travel code index
     * add travel code
     * add travel code name
     * @param tCode
     * @param tCodeName
     */
    public void addIndex(String tCode, String tCodeName){
        travelCode.add(tCode);
        travelCodeName.add(tCodeName);
        Debugger.showDebugMessage("[SUCCESS] SearchIndex - Data: "+tCode+" - "+tCodeName+" ADDED! "+travelCode.size()+" Data added.");
    }

    /**
     * get methods of travel code index components
     * get travelCode
     * get travelCodeName
     */
    public ArrayList<String> getTravelCode(){
        return travelCode;
    }
    public ArrayList<String> getTravelCodeName(){
        return travelCodeName;
    }

}
