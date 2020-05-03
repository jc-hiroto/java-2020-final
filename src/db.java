package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * this class is to define all logic used in database
 */
class db {
    private static Connection connection = null;
    private static String url = FilenameUtils.separatorsToSystem( "jdbc:sqlite:"+System.getProperty("user.dir")+ "/src/DB/trip_app.db");
    private static ArrayList <String> USER_NAME = new ArrayList<String>();
    private static ArrayList <String> USER_EMAIL = new ArrayList<String>();
    private static ArrayList <String> USER_PASS = new ArrayList<String>();
    private static ArrayList <String> title = new ArrayList<String>();
    private static ArrayList <String> product_key = new ArrayList<String>();
    private static ArrayList <String> price = new ArrayList<String>();
    private static ArrayList <String> travel_code = new ArrayList<String>();
    private static ArrayList <String> start_date = new ArrayList<String>();
    private static ArrayList <String> end_date = new ArrayList<String>();

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
        return  "INSERT INTO USER(USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES(\'"+userName+"\',\'"+userEmail+"\',\'"+userPass.toString()+"\')";
    }

    public static String userAuth(String email, StringBuffer password){
        String flag = "ERR";
        connectToDB();
        System.out.println("[Success] User data collected: "+ collectUserData());
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

    public static boolean checkUserExist(String email){
        boolean flag = false;
        connectToDB();
        collectUserData();
        for(int i=0; i<USER_EMAIL.size();i++){
            if(USER_EMAIL.get(i).equals(email)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean newUser(String name,String email,StringBuffer password){
        boolean flag = false;
        if(checkUserExist(email)){
            flag = false;
        }
        else{
            connectToDB();
            Statement stmt = null;
            try {
                connection.setAutoCommit(false);
                stmt = connection.createStatement();
                stmt.executeUpdate(getInsertSql(name, email, password));
                connection.commit();
                System.out.println("[Success] Create new user.");
                flag = true;
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                flag = false;
            }finally{
                closeConnection(stmt);
            }
        }
        return flag;
    }

    /**
     * Get the travel program title ex.馬達加斯加 猴麵包樹 夢幻生態天堂10天
     * @param travelCode
     * @return title of the selected travelCode program
     */
    public static src.TravelData getTitle(String travelCode){
        connectToDB();
        String sql = "SELECT " + travelCode;
        Statement stmt = null;
        title.clear();
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                title.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                return null;
            }
        }
        return new src.TravelData(title);
    }

    /**
     * Get the product key of the travel program ex. VDR0000007686
     * @param travelCode
     * @return product key of the selected travelCode program
     */
    public static src.TravelData getProductKey(String travelCode){
        connectToDB();
        String sql = "SELECT " + travelCode;
        Statement stmt = null;
        product_key.clear();
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                product_key.add(rs.getString("product_key"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return new src.TravelData(product_key);
    }

    /**
     * Get the price of the travel program ex.155900
     * @param travelCode
     * @return price of the selected travelCode program
     */
    public static src.TravelData getPrice(String travelCode){
        connectToDB();
        String sql = "SELECT " + travelCode;
        Statement stmt = null;
        price.clear();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                price.add(rs.getString("price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return new src.TravelData(price);
    }

    /**
     * Special method for user search
     * Get the travelCode under given price limit
     * Call other method after this to get specific program's title, product key, price, date
     * @param price_limit
     * @return travelCode number under price limit
     */
    public static src.TravelData getPriceBelowTravelCode(int price_limit){
        connectToDB();
        String sql = "SELECT travel_code where price <= " + Integer.toString(price_limit);
        Statement stmt = null;
        travel_code.clear();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                travel_code.add(rs.getString("travel_code"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return new src.TravelData(travel_code);
    }

    /**
     * Get the start date of the travel program ex.2020-03-12
     * @param travelCode
     * @return start date of the selected travelCode program
     */
    public static src.TravelData getStartDate(String travelCode){
        connectToDB();
        String sql = "SELECT " + travelCode;
        Statement stmt = null;
        start_date.clear();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                start_date.add(rs.getString("start_date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return new src.TravelData(start_date);
    }

    /**
     * Get the end date of the travel program ex.2020-03-21
     * @param travelCode
     * @return end date of the selected travelCode program
     */
    public static src.TravelData getEndDate(String travelCode){
        connectToDB();
        String sql = "SELECT " + travelCode;
        Statement stmt = null;
        end_date.clear();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                end_date.add(rs.getString("end_date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return new src.TravelData(end_date);
    }

    /**
     * Special method for user search
     * Get the travelCode between given start date & end date
     * Call other method after this to get specific program's title, product key, price, date
     * @param start_date_limit
     * @param end_date_limit
     * @return travelCode number between the date range
     */
    public static src.TravelData getDateBetweenTravelCode(String start_date_limit, String end_date_limit){
        connectToDB();
        String sql = "SELECT travel_code where " + start_date_limit + " > start_date and " + end_date_limit + " < end_date_limit";
        Statement stmt = null;
        travel_code.clear();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                travel_code.add(rs.getString("travel_code"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return new src.TravelData(travel_code);
    }
}
