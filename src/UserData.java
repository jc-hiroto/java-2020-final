package src;
import java.util.ArrayList;

/**
 * Data structure of UserData
 */
public class UserData {
    public static ArrayList <String> USER_NAME = new ArrayList<String>();
    public static ArrayList <String> USER_EMAIL = new ArrayList<String>();
    public static ArrayList <String> USER_PASS = new ArrayList<String>();
    public static ArrayList <Integer> USER_BALANCE = new ArrayList<Integer>();

    /**
     * default constructor of UserData
     */
    public UserData(){
        USER_NAME = new ArrayList<String>();
        USER_EMAIL = new ArrayList<String>();
        USER_PASS = new ArrayList<String>();
        USER_BALANCE = new ArrayList<Integer>();
    }

}