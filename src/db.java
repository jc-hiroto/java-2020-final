package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    public static void extractProductData(ResultSet rs) throws SQLException, ParseException {
        productDataList.clear();
        int index = -1;
        boolean exists = false;
        while (rs.next()) {
            exists = false;
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
                Date start = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("start_date"));
                Date end = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("end_date"));
                ProductCombination PCtemp = new ProductCombination(rs.getInt("price"),rs.getInt("upper_bound"),rs.getInt("lower_bound"),start,end);
                PDtmp.addCombination(PCtemp);
                System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
            }
            else {
                ProductData newPDtmp = new ProductData(rs.getString("title"),rs.getString("product_key"),rs.getString("travel_code"));
                Date start = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("start_date"));
                Date end = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("end_date"));
                ProductCombination newPCtemp = new ProductCombination(rs.getInt("price"),rs.getInt("upper_bound"),rs.getInt("lower_bound"),start,end);
                newPDtmp.addCombination(newPCtemp);
                productDataList.add(newPDtmp);
                System.out.println("[SUCCESS] Added New Data Set: "+newPDtmp.getKey());
            }
        }
        System.out.println("[INFO] Total data set amount: "+productDataList.size());
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
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            extractProductData(rs);
        } catch (SQLException | ParseException e) {
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

    public static ArrayList<ProductData> getPriceBetween(String travelCode, int price_limit_bottom, int price_limit_top) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE travel_code = \'" + travelCode + "\' and price BETWEEN \'" + price_limit_bottom + "\' and \'" + price_limit_top + "\'";
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            extractProductData(rs);
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
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
    public static ArrayList<ProductData> getDateBetween(String travelCode,String start_date_limit, String end_date_limit) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE travel_code = \'" + travelCode + "\' and start_date >= Date('" + start_date_limit + "') and end_date <= Date('" + end_date_limit + "')";
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            extractProductData(rs);
        } catch (SQLException |ParseException e) {
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
        }finally{
            boolean closeStats = closeConnection(stmt);
            if(!closeStats){
                return null;
            }
        }
        return productDataList;
    }

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
