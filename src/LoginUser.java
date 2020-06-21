package src;

/**
 * data structure of login user
 */
public class LoginUser {

    private static String userName = null;

    /**
     * get log in user name
     * abstract class
     * @return
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * set user name
     * @param userName
     */
    public static void setUserName(String userName) {
        LoginUser.userName = userName;
    }
}
