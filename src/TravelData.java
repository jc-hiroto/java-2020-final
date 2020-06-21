package src;

import java.util.ArrayList;

/**
 * Data structure of TravelData
 */
public class TravelData {
    ArrayList<String> passingData ;

    /**
     * overloading constructor of TravelData
     */
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
