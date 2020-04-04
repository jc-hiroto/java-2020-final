package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


class db {
    private Connection connection = null;
    private String url = "jdbc:sqlite:/Users/james/Documents/GitHub/java-2020-final/src/DB/trip_app.db";
    public boolean connectToDB() {

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

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getInsertSql(String userName,String userEmail,StringBuffer userPass) {
        return  "INSERT INTO USER (USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES ("+userName+",'"+userEmail+"',"+userPass.toString()+");";
    }

    public boolean newUser(String name,String email,StringBuffer password){
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data/test.db");
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.executeUpdate(getInsertSql(name, email, password));
            connection.commit();
            System.out.println("INSERT Table  successfully.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }finally{
            try {
                if(stmt != null){
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            try {
                if(connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
