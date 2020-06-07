package src.hash;

import java.util.ArrayList;
import java.lang.String;
import src.Debugger;

public class travelSearchIndex {
    private ArrayList<String> travelCode;
    private ArrayList<String> travelCodeName;
    travelSearchIndex(){
        travelCode = new ArrayList<String>();
        travelCodeName = new ArrayList<String>();
    }
    public void addIndex(String tCode, String tCodeName){
        travelCode.add(tCode);
        travelCodeName.add(tCodeName);
        Debugger.showDebugMessage("[SUCCESS] SearchIndex - Data: "+tCode+" - "+tCodeName+" ADDED! "+travelCode.size()+" Data added.");
    }
    public ArrayList<String> getTravelCode(){
        return travelCode;
    }
    public ArrayList<String> getTravelCodeName(){
        return travelCodeName;
    }

}
