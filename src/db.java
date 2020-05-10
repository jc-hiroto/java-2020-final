package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.util.ArrayList;
import  src.TravelData;
import  src.ProductData;
import  src.ProductCombination;
import java.util.Date;

/**
 * this class is to define all logic used in database
 */
class db {
    private static Connection connection = null;
    private static String url = FilenameUtils.separatorsToSystem( "jdbc:sqlite:"+System.getProperty("user.dir")+ "/src/DB/trip_app_optimized.db");
    private static ArrayList <String> USER_NAME = new ArrayList<String>();
    private static ArrayList <String> USER_EMAIL = new ArrayList<String>();
    private static ArrayList <String> USER_PASS = new ArrayList<String>();
    private static ArrayList <ProductData> productDataList = new ArrayList<ProductData>();
    private static ArrayList <ProductCombination> productDataCombination = new ArrayList<ProductCombination>();
    private static ProductCombination productCombination;

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
        System.out.println("[Success] User data collected: " + collectUserData(email));

        if(USER_EMAIL.toString().equals(email)){
            if(USER_PASS.toString().equals(password.toString())){
                flag = USER_NAME.toString();
                return flag;
            } else{
                return flag + "in user password";
            }
        } else{
            return flag + "in user email";
        }
    }

    public static String collectUserData(String user_email){
        String flag = "ERR";
        String sql = "SELECT USER_NAME, USER_PASS FROM USER WHERE USER_NAME = " + user_email;
        Statement stmt = null;
        USER_NAME.clear();
        USER_PASS.clear();
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
               USER_NAME.add(rs.getString("USER_NAME"));
               USER_PASS.add(rs.getString("USER_PASS"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return flag; // If there is a exception, return ERR for instead.
        } finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return flag;
            }
        }
        return USER_NAME.toString(); // If success, return user name.
    }

    public static boolean checkUserExist(String email){
        boolean flag = false;
        connectToDB();
        collectUserData(email);
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
     * Get the travel program all info
     * @param travelCode
     * @return all info of the selected travelCode program
     */
    public static ArrayList<ProductData> getResult(String travelCode){
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE travel_code = \'" + travelCode+"\'";
        Statement stmt = null;
        productDataList.clear();
        int index = -1;
        boolean exists = false;
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for(int i = 0; i<productDataList.size();i++){
                    if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                        index = i;
                        exists = true;
                        System.out.println("[INFO] Find Existing Data Set: "+productDataList.get(i).getKey());
                        break;
                    }
                }
                if(exists){
                    ProductData PDtmp = productDataList.get(index);
                    Date start = rs.getTimestamp("start_date");
                    Date end = rs.getTimestamp("end_date");
                    ProductCombination PCtemp = new ProductCombination(rs.getInt("price"),rs.getInt("upper_bound"),rs.getInt("lower_bound"),start,end);
                    PDtmp.addCombination(PCtemp);
                    System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                }
                else {
                    ProductData newPDtmp = new ProductData(rs.getString("title"),rs.getString("product_key"),rs.getString("travel_code"));
                    Date start = rs.getTimestamp("start_date");
                    Date end = rs.getTimestamp("end_date");
                    ProductCombination newPCtemp = new ProductCombination(rs.getInt("price"),rs.getInt("upper_bound"),rs.getInt("lower_bound"),start,end);
                    productDataList.add(newPDtmp);
                    System.out.println("[SUCCESS] Added New Data Set: "+newPDtmp.getKey());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //return all.add(flag);
        }finally {
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                return null;
            }
        }
        return productDataList;
    }



    /**
     * Special method for user search
     * Get the travelCode under given price limit
     * Call other method after this to get specific program's title, product key, price, date
     * @param price_limit
     * @return travelCode number under price limit

    public static TravelData getPriceBelowTravelCode(int price_limit){
        connectToDB();
        String sql = "SELECT travel_code FROM trip_data WHERE price <= " + Integer.toString(price_limit);
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
        return new TravelData(travel_code);
    }


    /**
     * Special method for user search
     * Get the travelCode between given start date & end date
     * Call other method after this to get specific program's title, product key, price, date
     * @param start_date_limit
     * @param end_date_limit
     * @return travelCode number between the date range
     *
    public static TravelData getDateBetweenTravelCode(String start_date_limit, String end_date_limit){
        connectToDB();
        String sql = "SELECT travel_code FROM trip_data WHERE start_date < " + start_date_limit + " and end_date_limit > " + end_date_limit;
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
        return new TravelData(travel_code);
    }
    */
}
