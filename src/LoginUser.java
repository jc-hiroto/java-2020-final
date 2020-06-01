package src;

public class LoginUser {

    private static String userName = null;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        LoginUser.userName = userName;
    }
}
