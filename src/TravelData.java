package src;

import java.util.ArrayList;

public class TravelData {
    ArrayList<String> passingData ;

    public TravelData(){
        passingData = null;
    }

    public TravelData(ArrayList<String> givenData){
        passingData = new ArrayList<String>(givenData);
    }

    public TravelData(TravelData oldData){
        passingData = new ArrayList<String>(oldData.passingData);
    }
}
