package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static String url = FilenameUtils.separatorsToSystem( "jdbc:sqlite:"+System.getProperty("user.dir")+ "/src/DB/trip_app_optimized.db");
    private static UserData usr = new UserData();
    private static ArrayList<ProductData> productDataList = new ArrayList<ProductData>();
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
                    //System.out.println("[INFO] Find Existing Data Set: "+productDataList.get(i).getKey());
                    break;
                }
            }
            if(exists){
                ProductData PDtmp = productDataList.get(index);
                Date start = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("start_date"));
                Date end = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("end_date"));
                ProductCombination PCtemp = new ProductCombination(rs.getInt("price"),rs.getInt("upper_bound"),rs.getInt("lower_bound"),rs.getInt("currentOrder"),start,end);
                PDtmp.addCombination(PCtemp);
                //System.out.println("[SUCCESS] Write New Combination into Existing Data Set!");
            }
            else {
                ProductData newPDtmp = new ProductData(rs.getString("title"),rs.getString("product_key"),rs.getString("travel_code"));
                Date start = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("start_date"));
                Date end = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("end_date"));
                ProductCombination newPCtemp = new ProductCombination(rs.getInt("price"),rs.getInt("upper_bound"),rs.getInt("lower_bound"),rs.getInt("currentOrder"),start,end);
                newPDtmp.addCombination(newPCtemp);
                productDataList.add(newPDtmp);
                //System.out.println("[SUCCESS] Added New Data Set: "+newPDtmp.getKey());
            }
        }
        System.out.println("[INFO] Total data set amount: "+productDataList.size());
    }

    /**
     * Special method for user search
     * Get the travel data info under given price limit
     * @param price_limit
     * @return all info of the selected program under price limit
     */
    public static ArrayList<ProductData> getResult(String travelCode,int price_limit_bottom, int price_limit_top,String start_date_limit, String end_date_limit, int lower_bound_limit,int upper_bound_limit, int sortType) throws SQLException {
        connectToDB();
        String sql = "SELECT * FROM trip_data WHERE";
        if(travelCode != ""){
            sql += " travel_code = \'" + travelCode+"\'";
        }
        if(price_limit_top != 0){
            if(!sql.endsWith("WHERE")){
                sql += " and";
            }
            sql += " price BETWEEN \'" + price_limit_bottom + "\' and \'" + price_limit_top + "\'";
        }
        if(start_date_limit != ""){
            if(!sql.endsWith("WHERE")){
                sql += " and";
            }
            sql += " start_date >= Date('" + start_date_limit + "') and end_date <= Date('" + end_date_limit + "')";
        }
        if(upper_bound_limit != 0){
            if(!sql.endsWith("WHERE")){
                sql += " and";
            }
            sql += " upper_bound <= '" + upper_bound_limit + "' and lower_bound >= '" + lower_bound_limit + "'";
        }
        if(sql.endsWith("WHERE")){
            sql = sql.substring(0, sql.length()-6);
        }
        switch(sortType){
            case 0:
                break;
            case 1:
                sql += " ORDER BY price ASC";
                break;
            case 2:
                sql += " ORDER BY price DESC";
                break;
            case 3:
                sql += " ORDER BY start_date ASC";
                break;
            case 4:
                sql += " ORDER BY start_date DESC";
                break;
        }
        System.out.println(sql);
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
}