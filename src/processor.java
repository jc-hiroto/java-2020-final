package src;

public class processor{
    //public db dbObject = null;

    public processor(){
        //dbObject();  //link to DB and initial DB
    }

    /**
     * 
     * @param destination : input as string , enter by user
     * @param date : input as string , enter by user
     * @param sortBy : false = sort by price , true = sort by date , CHOOSE by user
     * @return not sure, maube a list or a string
     */
    //public list searchAvailableTrip(String destination , String date,boolean sortBy){

    //}

    /**
     *  ***ALL PARAMETER expect numberOFReverse should be enter automatically by system , not user.
     * @param id 
     * @param leavingDate
     * @param arrivalDate
     * @param numberOfReverse
     * @param productKey
     * @return : string ((Success or fail) + info)
     */
    public String reserveTrip(String id , String leavingDate , String arrivalDate , int numberOfReverse , int productKey ){
        return null;
    }

    /**
     * 
     * @param id
     * @param orderId
     * @param editNum : if want to remove the order , enter 0
     * @return
     */
    public String editTrip(String id , int orderId , int editNum){
        return null;
    }

    /**
     * 
     * @param id
     * @param orderId
     * @return
     */
    public String searchOrder(String id , int orderId){
        return null;
    }

    
}