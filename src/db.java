package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


class db {
    private static Connection connection = null;
    private static String url = "jdbc:sqlite:/Users/james/Documents/GitHub/java-2020-final/src/DB/trip_app.db";
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

    public static String getInsertSql(String userName,String userEmail,StringBuffer userPass) {
        System.out.println("INSERT INTO USER(USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES(\'"+userName+"\',\'"+userEmail+"\',\'"+userPass.toString()+"\')");
        return  "INSERT INTO USER(USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES(\'"+userName+"\',\'"+userEmail+"\',\'"+userPass.toString()+"\')";
    }

    public static boolean newUser(String name,String email,StringBuffer password){
        Statement stmt = null;
        boolean flag = false;
        try {
            Class.forName("org.sqlite.JDBC");
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
            try {
                if(stmt != null){
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
