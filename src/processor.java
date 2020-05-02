package src;
import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemEventListener;

public class Processor {
    //public db dbObject = null;

    public Processor(){
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

    /**
     * Check if email address is valid . (Call in login and register.java)
     * @param mailAddress
     * @return
     */
    public static boolean mailAddressValidChecker(String mailAddress){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(mailAddress);
    }


    public static String textLineShifter(JTextArea targetarea,String rawString,int windowsSize,int leftOffset,int rightOffset) {
        int totalWidth = windowsSize - leftOffset - rightOffset;
        StringBuilder stringb = new StringBuilder();
        rawString=rawString.replaceAll("\n","");
        char[] chars = rawString.toCharArray();
        FontMetrics fontMetrics = targetarea.getFontMetrics(targetarea.getFont());
        int start = 0;
        int oneLength = 0;
        while (start + oneLength < rawString.length()) {
            while (true) {
                oneLength++;
                if (start + oneLength > rawString.length()) {
                    break;
                }
                if (fontMetrics.charsWidth(chars, start, oneLength) > totalWidth) {
                    break;
                }
            }
            stringb.append(chars, start, oneLength - 1).append("\n");
            start = start + oneLength - 1;
            oneLength = 0;
        }
        return stringb.toString();
    }
}