package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.ArrayList;

import  src.TravelData;
import  src.UserData;
import  src.ProductData;
import  src.ProductCombination;

/**
 * this class is to define all logic used in database
 */
class db {
    private static Connection connection = null;
    private static String url = FilenameUtils.separatorsToSystem( "jdbc:sqlite:"+System.getProperty("user.dir")+ "/src/DB/trip_app.db");
    private static UserData usr = new UserData();

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

    /**
     * insert the user info into the database
     * @param userName
     * @param userEmail
     * @param userPass
     * @return user name if true
     */
    public static String getInsertSql(String userName,String userEmail,StringBuffer userPass) {
        return  "INSERT INTO USER(USER_NAME,USER_EMAIL,USER_PASS) " +
                "VALUES(\'"+userName+"\',\'"+userEmail+"\',\'"+userPass.toString()+"\')";
    }

    public static String userAuth(String email, StringBuffer password){
        String flag = "ERR";
        connectToDB();
        System.out.println("[Success] User data collected: "+ collectUserData());
        for(int i = 0; i<usr.USER_NAME.size(); i++){
            if(usr.USER_EMAIL.get(i).equals(email)){
                if(usr.USER_PASS.get(i).equals(password.toString())){
                    flag = usr.USER_NAME.get(i);
                }
                else{
                    flag = "ERR";
                }
            }
        }
        return flag;
    }

    /**
     * collect all user data
     * @return user name size if true
     */
    public static int collectUserData(){
        String sql = "SELECT USER_NAME, USER_EMAIL, USER_PASS FROM USER";
        Statement stmt = null;
        usr.USER_NAME.clear();
        usr.USER_EMAIL.clear();
        usr.USER_PASS.clear();
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                usr.USER_NAME.add(rs.getString("USER_NAME"));
                usr.USER_EMAIL.add(rs.getString("USER_EMAIL"));
                usr.USER_PASS.add(rs.getString("USER_PASS"));
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
        return usr.USER_NAME.size(); // If success, return the number of user collected.
    }

    /**
     * check if the user exists or not
     * @param email
     * @return true if exists
     */
    public static boolean checkUserExist(String email){
        boolean flag = false;
        connectToDB();
        collectUserData();
        for(int i=0; i<usr.USER_EMAIL.size();i++){
            if(usr.USER_EMAIL.get(i).equals(email)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * create new user method on user system
     * @param name
     * @param email
     * @param password
     * @return true is success
     */
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
    public static ArrayList<ProductData> getAll(String travelCode) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE travel_code = \'" + travelCode + "\' ORDER BY product_key DESC";
        Statement stmt = null;
        ArrayList<ProductData> productDataList = new ArrayList<ProductData>();

        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            for (int i = 0; i < productDataList.size(); i++){
                if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                    ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                    do{
                        ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                        pdd.detail.add(pdc);
                        System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                    } while(rs.next());
                    productDataList.add(pdd);
                    System.out.println("[SUCCESS] Added New Data Set: " + pdd.getKey());
                }else{
                    System.out.println("[INFO] Find Existing Data Set: " + productDataList.get(i).getKey());
                    break;
                }
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
    public static ArrayList<ProductData> getPriceBelow(int price_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE price <= \'" + price_limit + "\' ORDER BY product_key DESC";
        Statement stmt = null;
        ArrayList<ProductData> productDataList = new ArrayList<ProductData>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            for (int i = 0; i < productDataList.size(); i++){
                if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                    ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                    do{
                        ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                        pdd.detail.add(pdc);
                        System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                    } while(rs.next());
                    productDataList.add(pdd);
                    System.out.println("[SUCCESS] Added New Data Set: " + pdd.getKey());
                }else{
                    System.out.println("[INFO] Find Existing Data Set: " + productDataList.get(i).getKey());
                    break;
                }
            }

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
    public static ArrayList<ProductData> getPriceBetween(int price_limit_bottom, int price_limit_top) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE price BETWEEN \'" + price_limit_bottom + "' and \'" + price_limit_top + "\' ORDER BY product_key DESC";
        Statement stmt = null;
        ArrayList<ProductData> productDataList = new ArrayList<ProductData>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            for (int i = 0; i < productDataList.size(); i++){
                if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                    ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                    do{
                        ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                        pdd.detail.add(pdc);
                        System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                    } while(rs.next());
                    productDataList.add(pdd);
                    System.out.println("[SUCCESS] Added New Data Set: " + pdd.getKey());
                }else{
                    System.out.println("[INFO] Find Existing Data Set: " + productDataList.get(i).getKey());
                    break;
                }
            }

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
    public static ArrayList<ProductData> getDateBetween(String start_date_limit, String end_date_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE start_date <= \'" + start_date_limit + "\' and end_date_limit >= \'" + end_date_limit + "\' ORDER BY product_key DESC";
        Statement stmt = null;
        ArrayList<ProductData> productDataList = new ArrayList<ProductData>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            for (int i = 0; i < productDataList.size(); i++){
                if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                    ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                    do{
                        ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                        pdd.detail.add(pdc);
                        System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                    } while(rs.next());
                    productDataList.add(pdd);
                    System.out.println("[SUCCESS] Added New Data Set: " + pdd.getKey());
                }else{
                    System.out.println("[INFO] Find Existing Data Set: " + productDataList.get(i).getKey());
                    break;
                }
            }

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
    public static ArrayList<ProductData> getPeopleBetween(int upper_bound_limit, int lower_bound_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE upper_bound <= \'" + upper_bound_limit + "\' and lower_bound >= \'" + lower_bound_limit + "\' ORDER BY product_key DESC";
        Statement stmt = null;
        ArrayList<ProductData> productDataList = new ArrayList<ProductData>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            for (int i = 0; i < productDataList.size(); i++){
                if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                    ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                    do{
                        ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                        pdd.detail.add(pdc);
                        System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                    } while(rs.next());
                    productDataList.add(pdd);
                    System.out.println("[SUCCESS] Added New Data Set: " + pdd.getKey());
                }else{
                    System.out.println("[INFO] Find Existing Data Set: " + productDataList.get(i).getKey());
                    break;
                }
            }

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
    public static ArrayList getPeopleBelow(int upper_bound_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE upper_bound <= \'" + upper_bound_limit + "\' ORDER BY product_key DESC";
        Statement stmt = null;
        ArrayList<ProductData> productDataList = new ArrayList<ProductData>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            for (int i = 0; i < productDataList.size(); i++){
                if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                    ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                    do{
                        ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                        pdd.detail.add(pdc);
                        System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                    } while(rs.next());
                    productDataList.add(pdd);
                    System.out.println("[SUCCESS] Added New Data Set: " + pdd.getKey());
                }else{
                    System.out.println("[INFO] Find Existing Data Set: " + productDataList.get(i).getKey());
                    break;
                }
            }

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
    public static ArrayList getPeopleAbove(int lower_bound_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE lower_bound >= \'" + lower_bound_limit + "\' ORDER BY product_key DESC";
        Statement stmt = null;
        ArrayList<ProductData> productDataList = new ArrayList<ProductData>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            for (int i = 0; i < productDataList.size(); i++){
                if(productDataList.get(i).getKey().equals(rs.getString("product_key"))){
                    ProductData pdd = new ProductData(rs.getString("title"), rs.getString("product_key"), rs.getString("travel_code"));
                    do{
                        ProductCombination pdc = new ProductCombination(rs.getInt("price"), rs.getInt("lower_bound"), rs.getInt("high_bound"), rs.getDate("start_date"), rs.getDate("end_date"));
                        pdd.detail.add(pdc);
                        System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
                    } while(rs.next());
                    productDataList.add(pdd);
                    System.out.println("[SUCCESS] Added New Data Set: " + pdd.getKey());
                }else{
                    System.out.println("[INFO] Find Existing Data Set: " + productDataList.get(i).getKey());
                    break;
                }
            }

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