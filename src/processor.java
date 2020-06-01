package src;
import org.apache.commons.validator.routines.EmailValidator;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemEventListener;

public class Processor {
    private static final int []travelCode= {40,41,43,44,100,101,342,343,368,384,391,392,393,394,395,396,397,398,399,
            401,404,405,406,407,408,409,410,411,412,413,414,415,416,417,426,427,428,430,431,432,433,435,436,439,440,441,442};

    /**
     *  ***ALL PARAMETER expect numberOFReverse should be enter automatically by system , not user.
     * @param id 
     * @param leavingDate
     * @param arrivalDate
     * @param numberOfReverse
     * @param productKey
     * @return : string ((Success or fail) + info)
     */
    public static String reserveTrip(String id , String leavingDate , String arrivalDate , int numberOfReverse , int productKey ){
        return null;
    }

    /**
     * 
     * @param id
     * @param orderId
     * @param editNum : if want to remove the order , enter 0
     * @return
     */
    public static String editOrder(String id , int orderId , int editNum){
        return null;
    }

    /**
     * 
     * @param id
     * @param orderId
     * @return
     */
    public static String searchOrder(String id , int orderId){
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

    public static String textCutter(String rawString, int size, FontMetrics fontMetrics){
        StringBuilder stringb = new StringBuilder();
        stringb.append("<html><body><p>");
        rawString=rawString.replaceAll("\n","");
        char[] chars = rawString.toCharArray();
        int start = 0;
        int oneLength = 0;
        while (start + oneLength < rawString.length()) {
            while (true) {
                oneLength++;
                if (start + oneLength > rawString.length()) {
                    break;
                }
                if (fontMetrics.charsWidth(chars, start, oneLength) > size) {
                    break;
                }
            }
            stringb.append(chars, start, oneLength - 1).append("<br>");
            start = start + oneLength - 1;
            oneLength = 0;
        }
        stringb.append("</p></body></html>");
        return stringb.toString();
    }

    public static int randomTravelCodeGene(){
        return travelCode[(int)((Math.random()*100)%travelCode.length)];
    }

    public static String newOrderNumberGenerator(String lastNumber){
        String subNumber = lastNumber.replaceAll("[^0-9]", "");
        System.out.println("LAST: "+lastNumber);
        System.out.println("SUB: "+subNumber);
        int numberPart = Integer.parseInt(subNumber);
        numberPart++;
        String newOrderNumber = lastNumber.substring(0,3) + Integer.toString(numberPart);
        System.out.println("NEW: "+newOrderNumber);
        return newOrderNumber;
    }
}