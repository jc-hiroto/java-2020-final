package src;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


class db {
    private static Connection connection = null;
    private static String url = "jdbc:sqlite:/Users/james/Documents/GitHub/java-2020-final/src/DB/trip_app.db";
    private static ArrayList <String> USER_NAME = new ArrayList<String>();
    private static ArrayList <String> USER_EMAIL = new ArrayList<String>();
    private static ArrayList <String> USER_PASS = new ArrayList<String>();
    public static boolean connectToDB() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (connection == null) {
            return false;
        }
        return true;
    }
    public static boolean closeConnection(Statement stmt){
        boolean flag = false;
        try {
            if(stmt != null){
                stmt.close();
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        try {
            if(connection != null){
                connection.close();
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public static String getInsertSql(String userName,String userEmail,StringBuffer userPass) {
        System.out.println("INSERT INTO USER(USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES(\'"+userName+"\',\'"+userEmail+"\',\'"+userPass.toString()+"\')");
        return  "INSERT INTO USER(USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES(\'"+userName+"\',\'"+userEmail+"\',\'"+userPass.toString()+"\')";
    }

    public static String userAuth(String email, StringBuffer password){
        String flag = "ERR";
        connectToDB();
        System.out.println("User data collected: "+ collectUserData());
        for(int i = 0; i<USER_NAME.size(); i++){
            if(USER_EMAIL.get(i).equals(email)){
                if(USER_PASS.get(i).equals(password.toString())){
                    flag = USER_NAME.get(i);
                }
                else{
                    flag = "ERR";
                }
            }
        }
        return flag;
    }

    public static int collectUserData(){
        String sql = "SELECT USER_NAME, USER_EMAIL, USER_PASS FROM USER";
        Statement stmt = null;
        USER_NAME.clear();
        USER_EMAIL.clear();
        USER_PASS.clear();
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
               USER_NAME.add(rs.getString("USER_NAME"));
               USER_EMAIL.add(rs.getString("USER_EMAIL"));
               USER_PASS.add(rs.getString("USER_PASS"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1; // If there is a exception, return -1 for instead.
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return -1;
            }
        }
        return USER_NAME.size(); // If success, return the number of user collected.
    }

    public static boolean newUser(String name,String email,StringBuffer password){
        Statement stmt = null;
        boolean flag = false;
        try {
            connectToDB();
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.executeUpdate(getInsertSql(name, email, password));
            connection.commit();
            System.out.println("INSERT Table  successfully.");
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            flag = false;
        }finally{
            closeConnection(stmt);
        }
        return flag;
    }
}
