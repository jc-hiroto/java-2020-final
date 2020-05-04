package src.hash;

import java.util.ArrayList;
import java.lang.String;

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
        System.out.println("Data: "+tCode+" - "+tCodeName+" ADDED! "+travelCode.size()+" Data added.");
    }
    public String search(String searchWord){
        for(int i = 0; i < travelCode.size(); i++){
            if(travelCodeName.get(i).contains(searchWord)){
                System.out.println("Using word: "+searchWord+" Find result TravelCode: "+travelCode.get(i)+" for category: "+ travelCodeName.get(i));
                return travelCode.get(i);
            }
        }
        System.out.println("Using word: "+searchWord+". Result not found!");
        return "ERR";
    }
}
