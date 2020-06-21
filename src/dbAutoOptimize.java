package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * define common method used by classes including following method
 * connect to database
 * define the JDBC
 * close connection to database
 * auto-generate key through hash
 */
public class dbAutoOptimize {
    private static Connection connection = null;
    private static String url = FilenameUtils.separatorsToSystem( "jdbc:sqlite:"+System.getProperty("user.dir")+ "/src/DB/trip_app_optimized.db");
    private static ArrayList<String> done = new ArrayList<String>();

    /**
     * connect to database
     * @return true if success
     */
    public static boolean connectToDB() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("[Success] Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (connection == null) {
            return false;
        }
        return true;
    }

    /**
     * close connection to database
     * @param stmt
     * @return true if success
     */
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

    /**
     * auto generate key using hash
     * @param title
     * @return String key
     */
    public static String generateKey(String title){
        String hashed = Integer.toString(Math.abs(title.hashCode()));
        String key;
        switch (hashed.length()){
            case 9:
                key = "VDR0" + hashed;
                break;
            case 8:
                key = "VDR00" + hashed;
                break;
            default:
                key = "VDR" + hashed;
                break;
        }
        return key;
    }

    /**
     * main class of dbAutoOptimize
     * @param args
     */
    public static void main(String[] args){
        connectToDB();
        String sql = "SELECT title FROM trip_data";
        Statement stmt = null;
        Statement stmt1 = null;
        try {
            stmt  = connection.createStatement();
            stmt1  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            int counter = 1;
            while (rs.next()) {
                String title = rs.getString("title");
                System.out.println("SUCCESSFULLY GET TITLE - "+counter+" : "+ title);
                if(!done.contains(title)){
                    String key = generateKey(title);
                    System.out.println("GENERATED KEY: "+ key);
                    String sqlUpdate = "UPDATE trip_data " +
                            "SET product_key = \'"+key+"\' WHERE title =\'"+title+"\'";
                    stmt1.execute(sqlUpdate);
                    done.add(title);
                    counter++;
                }
                else{
                    System.out.println("SKIP DATA: DATA HAS BEEN PROCESSED!");
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally{
            closeConnection(stmt1);
            closeConnection(stmt);
        }
    }
}
