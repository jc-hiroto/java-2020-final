package src;
import java.security.SecureRandom;
import java.util.ArrayList;

public class UserData {
    public static ArrayList <String> USER_NAME = new ArrayList<String>();
    public static ArrayList <String> USER_EMAIL = new ArrayList<String>();
    public static ArrayList <String> USER_PASS = new ArrayList<String>();
    public static ArrayList <Integer> USER_BALANCE = new ArrayList<Integer>();

    public UserData(){
        USER_NAME = new ArrayList<String>();
        USER_EMAIL = new ArrayList<String>();
        USER_PASS = new ArrayList<String>();
        USER_BALANCE = new ArrayList<Integer>();
    }

}