package src;
import java.security.SecureRandom;
import java.util.ArrayList;

public class UserData {
    public static ArrayList <String> USER_NAME = new ArrayList<String>();
    public static ArrayList <String> USER_EMAIL = new ArrayList<String>();
    public static ArrayList <String> USER_PASS = new ArrayList<String>();
    public static ArrayList <int> USER_BALANCE = new ArrayList<int>();

    public UserData(){
        USER_NAME = null;
        USER_EMAIL = null;
        USER_PASS = null;
        USER_BALANCE = 0;
    }

}