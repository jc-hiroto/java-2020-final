package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import  src.TravelData;
import  src.ProductData;
import  src.ProductCombination;

/**
 * this class is to define all logic used in database
 */
class db {
    private static Connection connection = null;
    private static String url = FilenameUtils.separatorsToSystem( "jdbc:sqlite:"+System.getProperty("user.dir")+ "/src/DB/trip_app.db");
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
        String sql = "SELECT USER_NAME, USER_PASS FROM USER WHERE USER_NAME = '" + user_email + "'";
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
    public static List getAll(String travelCode) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE travel_code = '" + travelCode + "'";
        Statement stmt = null;
        List<Object> productDataList = new ArrayList<Object>();

        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                do{
                    ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                    pdd.detail.add(pdc);
                } while(rs.next());
                productDataList.add(pdd);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            throw e;
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
     * Get the travel data info under given price limit
     * @param price_limit
     * @return all info of the selected program under price limit
     */
    public static List getPriceBelow(int price_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE price <= '" + price_limit + "'";
        Statement stmt = null;
        List<Object> productDataList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
            do{
                ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                pdd.detail.add(pdc);
            } while(rs.next());
            productDataList.add(pdd);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            throw e;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return productDataList;
    }

    /**
     * Special method for user search
     * Get the travel data info between given price range
     * @param price_limit_bottom
     * @param price_limit_top
     * @return all info of the selected program under price limit
     */
    public static List getPriceBetween(int price_limit_bottom, int price_limit_top) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE price BETWEEN '" + price_limit_bottom + "' and '" + price_limit_top + "'";
        Statement stmt = null;
        List<Object> productDataList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
            do{
                ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                pdd.detail.add(pdc);
            } while(rs.next());
            productDataList.add(pdd);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            throw e;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return productDataList;
    }

    /**
     * Special method for user search
     * Get the travel data info between given start date & end date
     * @param start_date_limit
     * @param end_date_limit
     * @return all info of the selected program under date limit
     */
    public static List getDateBetween(String start_date_limit, String end_date_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE start_date <= '" + start_date_limit + "' and end_date_limit >= '" + end_date_limit + "'";
        Statement stmt = null;
        List<Object> productDataList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
            do{
                ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                pdd.detail.add(pdc);
            } while(rs.next());
            productDataList.add(pdd);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            throw e;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return productDataList;
    }

    /**
     * Special method for user search
     * Get the travel data info between given upper_bound and lower_bound
     * @param upper_bound_limit
     * @param lower_bound_limit
     * @return all info of the selected program under people limit
     */
    public static List getPeopleBetween(int upper_bound_limit, int lower_bound_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE upper_bound <= '" + upper_bound_limit + "' and lower_bound >= '" + lower_bound_limit + "'";
        Statement stmt = null;
        List<Object> productDataList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
            do{
                ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                pdd.detail.add(pdc);
            } while(rs.next());
            productDataList.add(pdd);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            throw e;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return productDataList;
    }

    /**
     * Special method for user search
     * Get the travel data info under given upper_bound limit
     * @param upper_bound_limit
     * @return all info of the selected program under people limit
     */
    public static List getPeopleBelow(int upper_bound_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE upper_bound <= '" + upper_bound_limit + "'";
        Statement stmt = null;
        List<Object> productDataList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
            do{
                ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                pdd.detail.add(pdc);
            } while(rs.next());
            productDataList.add(pdd);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            throw e;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return productDataList;
    }

    /**
     * Special method for user search
     * Get the travel data info under given lower_bound limit
     * @param lower_bound_limit
     * @return all info of the selected program under people limit
     */
    public static List getPeopleAbove(int lower_bound_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE lower_bound >= '" + lower_bound_limit + "'";
        Statement stmt = null;
        List<Object> productDataList = new ArrayList<Object>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
            do{
                ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                pdd.detail.add(pdc);
            } while(rs.next());
            productDataList.add(pdd);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
            throw e;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return productDataList;
    }
}