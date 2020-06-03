package src;

import org.apache.commons.io.FilenameUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * this class is to define all logic used in database
 */
class db {
    private static Connection connection = null;
    private static String url = FilenameUtils.separatorsToSystem( "jdbc:sqlite:"+System.getProperty("user.dir")+ "/src/DB/trip_app_optimized.db");
    private static UserData usr = new UserData();
    private static ArrayList<ProductData> productDataList = new ArrayList<ProductData>();
    public static ArrayList<Order> orderList = new ArrayList<Order>();
    public static ArrayList<Favorite> favList = new ArrayList<Favorite>();
    public static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * set connection to backend database
     * @return true if connect successfully
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
     * close the connection with database
     * @param stmt
     * @return flag true if success
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
     * insert the user info into the database
     * @param userName
     * @param userEmail
     * @param userPass
     * @return user name if true
     */
    public static String getInsertSql(String userName,String userEmail,StringBuffer userPass, int userBalance) {
        return  "INSERT INTO USER(USER_NAME,USER_EMAIL,USER_PASS,USER_BALANCE) " +
                "VALUES(\'"+userName+"\',\'"+userEmail+"\',\'"+userPass.toString()+"\',\'"+userBalance+"\')";
    }

    /**
     * authorize user email, password
     * @param email
     * @param password
     * @return status
     */
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
        String sql = "SELECT USER_NAME, USER_EMAIL, USER_PASS, USER_BALANCE FROM USER";
        Statement stmt = null;
        usr.USER_NAME.clear();
        usr.USER_EMAIL.clear();
        usr.USER_PASS.clear();
        usr.USER_BALANCE.clear();
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                usr.USER_NAME.add(rs.getString("USER_NAME"));
                usr.USER_EMAIL.add(rs.getString("USER_EMAIL"));
                usr.USER_PASS.add(rs.getString("USER_PASS"));
                usr.USER_BALANCE.add(rs.getInt("USER_BALANCE"));
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
    public static boolean newUser(String name, String email, StringBuffer password, int balance){
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
                stmt.executeUpdate(getInsertSql(name, email, password, balance));
                connection.commit();
                System.out.println("[Success] Created new user.");
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
     * get product data data from database
     * @param rs
     * @throws SQLException
     * @throws ParseException
     */
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
        System.out.println("[SUCCESS] Total data set dumped: "+productDataList.size());
    }

    /**
     * get the trip program title by product key
     * @param productKey
     * @return title String
     */
    public static String getTitleByKey(String productKey){
        connectToDB();
        String sql = "SELECT title FROM trip_data WHERE product_key = \'"+productKey+"\'";
        Statement stmt = null;
        String productName = null;
        try{
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            productName = rs.getString("title");
            System.out.println("[INFO] GET TITLE:"+ productName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                return null;
            }
        }
        return productName;
    }

    /**
     * Special method for user search
     * Get the travel data info under given price limit
     * @param travelCode
     * @param price_limit_bottom
     * @param price_limit_top
     * @param start_date_limit
     * @param end_date_limit
     * @param lower_bound_limit
     * @param upper_bound_limit
     * @param sortType
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
        System.out.println("[SQL INFO] "+sql);
        Statement stmt = null;
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            extractProductData(rs);
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }finally {
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                return null;
            }
        }
        return productDataList;
    }

    /**
     * execute the the sql that insert new order
     * @param orderNumber
     * @param productKey
     * @param orderStatus
     * @param orderAmount
     * @param order_start_date
     * @param order_order_date
     * @return flag true if success
     */
    private static boolean insertOrder(String orderNumber, String productKey, String orderStatus, String orderAmount, Date order_start_date, Date order_order_date, String userName){
        boolean flag = false;
        connectToDB();
        Statement stmt = null;
        String sql = "INSERT INTO order_data(Order_number, Order_ProductKey, Order_status, Order_amount, Order_StartDate, Order_orderDate, Order_user)" +
                "VALUES(\'"+orderNumber+"\',\'"+productKey+"\',\'"+orderStatus+"\', \'"+orderAmount+"\', \'"+sdf.format(order_start_date)+"\', \'"+sdf.format(order_order_date)+"\', \'" + userName + "\')";
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            connection.commit();
            System.out.println("[Success] Create new order.");
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            flag = false;
        }finally{
            closeConnection(stmt);
        }
        return flag;
    }

    /**
     * execute the sql that update the current order amount
     * @param CurOrder
     * @param productKey
     * @param order_start_date
     * @param PC
     * @return flag true if success
     */
    private static boolean updateCurOrder(int CurOrder, String productKey, Date order_start_date,ProductCombination PC){
        boolean flag = false;
        connectToDB();
        Statement stmt = null;
        String sql = "UPDATE trip_data SET currentOrder = " + CurOrder + " WHERE product_key = \'" + productKey + "\' and start_date = \'" + sdf.format(order_start_date) + "\'";
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            connection.commit();
            PC.setCurrentOrder(CurOrder);
            System.out.println("[Success] Update current order amount to: "+CurOrder);
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            flag = false;
        }finally{
            closeConnection(stmt);
        }
        return flag;
    }

    /**
     * update current order number of program
     * @param CurOrder
     * @param productKey
     * @param order_start_date
     * @return
     */
    private static boolean updateCurOrder(int CurOrder, String productKey, Date order_start_date){
        boolean flag = false;
        connectToDB();
        Statement stmt = null;
        String sql = "UPDATE trip_data SET currentOrder = " + CurOrder + " WHERE product_key = \'" + productKey + "\' and start_date = \'" + sdf.format(order_start_date) + "\'";
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            connection.commit();
            System.out.println("[Success] Update current order amount to: "+CurOrder);
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            flag = false;
        }finally{
            closeConnection(stmt);
        }
        return flag;
    }

    /**
     * get the order number of the last order record
     * @return String, order number
     */
    public static String getLastOrderNo(){
        connectToDB();
        String sql="SELECT Order_number FROM order_data ORDER BY Order_number DESC";
        Statement stmt = null;
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getString("Order_number");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                return null;
            }
        }
        return null;
    }

    /**
     * set new order
     * @param userName
     * @param PD
     * @param PC
     * @param amount
     * @return flag if success
     */
    public static int newOrder(String userName, ProductData PD, ProductCombination PC, int amount){
        int flag = 1;
        String orderNumber = Processor.newOrderNumberGenerator(getLastOrderNo());
        String status = "";
        if(PC.getUpperBound() >= (PC.getCurrentOrder() + amount)){
            status = "OKAY";
            // Insert new order in database
            boolean insertFlag = insertOrder(orderNumber, PD.getKey(), status, Integer.toString(amount), PC.getStartDate(), new Date(), userName);
            // Update current order amount in database
            boolean updateFlag = updateCurOrder((PC.getCurrentOrder() + amount), PD.getKey(),PC.getStartDate(),PC);
            if(!insertFlag || !updateFlag){
                System.out.println("[ERROR] System fatal error");
                flag = -2;
            }
            else{
                flag = 0;
            }
        }
        else{
            System.out.println("[ERROR] Not enough seat.");
            flag = -1;
        }
        return flag;
    }

    /**
     * modify order
     * @param
     * @param
     * @param amount
     * @return flag true is success
     */
    public static int updateOrder(Order ord, int amount){
        int flag = 1;
        boolean delFlag = false;
        boolean newFlag = false;
        boolean updateFlag = false;
        String newOrderNumber = Processor.newOrderNumberGenerator(getLastOrderNo());
        connectToDB();
        Statement stmt = null;
        String sql = "";
        int currentOrder = 0,upper_bound = 0;
        try {
            stmt = connection.createStatement();
            sql = "SELECT * from trip_data WHERE product_key = \'" + ord.getKey() + "\' AND start_date = \'"+ sdf.format(ord.getStartDate()) +"\'";
            ResultSet rs1 = stmt.executeQuery(sql);
            rs1.next();
            currentOrder = rs1.getInt("currentOrder");
            upper_bound = rs1.getInt("upper_bound");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            flag = -2; //system Failed
        }finally{
            closeConnection(stmt);
        }
        try {
            if(currentOrder + amount - ord.getNum() <= upper_bound){
                delFlag = deleteOrder(ord);
                newFlag = insertOrder(newOrderNumber, ord.getKey(), "OKAY",Integer.toString(amount), ord.getStartDate(), new Date(), ord.getUsr());
                updateFlag = updateCurOrder((currentOrder + amount - ord.getNum()), ord.getKey(),ord.getStartDate());
                System.out.println("[SUCCESS] Already set your amount to" + amount);
                flag = 0;
            }
            else{
                flag = -1; // order is full
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            flag = -2; //system Failed
        }
        if(!updateFlag)
            flag = -3;
        if(!newFlag)
            flag = -4;
        if(!delFlag)
            flag = -5;
        return flag;
    }

    /**
     * remove order
     * @param
     * @return flag true is success
     */
    public static boolean deleteOrder(Order ord){
        boolean flag = false;
        connectToDB();
        String sql = "Update order_data SET Order_status = \'CANCELED\' WHERE Order_number = \'" + ord.getOrderNum() + "\'";
        String sql3 = "SELECT * FROM trip_data WHERE product_key = \'";
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            //System.out.println(sql3 + ord.getKey() + "\' AND start_date = \'"+ sdf.format(ord.getStartDate()) +"\'");
            ResultSet rs3 = stmt.executeQuery(sql3 + ord.getKey() + "\' AND start_date = \'"+ sdf.format(ord.getStartDate()) +"\'");
            rs3.next();

            System.out.println("Update trip_data SET currentOrder = \'"+ (rs3.getInt("currentOrder") - ord.getNum()) + "\' WHERE product_key = \'"+  ord.getKey() + "\'AND start_date = \'"+sdf.format(ord.getStartDate())+"\'");
            stmt.executeUpdate("Update trip_data SET currentOrder = \'"+ (rs3.getInt("currentOrder") - ord.getNum()) + "\' WHERE product_key = \'"+  ord.getKey() + "\'AND start_date = \'"+sdf.format(ord.getStartDate())+"\'");
            stmt.executeUpdate(sql);
            connection.commit();
            System.out.println("[SUCCESS] Already cancel your order.");
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            flag = false;
        }finally{
            closeConnection(stmt);
        }
        return flag;
    }

    /**
     * get the order number by user
     * @param userName
     * @return orderList
     * @throws SQLException
     */
    public static ArrayList<Order> getOrderByUser(String userName) throws SQLException {
        orderList.clear();
        connectToDB();
        Statement stmt = null;
        String sql = "SELECT * FROM order_data WHERE Order_user = \'"+userName+"\'";
        try {
            stmt  = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Date startDate = (Date) sdf.parse(rs.getString("Order_StartDate"));
                Date orderDate = (Date) sdf.parse(rs.getString("Order_orderDate"));
                Order orderTmp = new Order(rs.getString("Order_number"), rs.getString("Order_ProductKey"), rs.getString("Order_status"), rs.getString("Order_user"), rs.getInt("Order_amount"), startDate, orderDate);
                orderList.add(orderTmp);
            }
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }finally {
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                return null;
            }
        }
        return orderList;
    }

    /**
     * get order record by orderNumber
     * @param orderNumber
     * @return Order
     */
    public static Order getOrderByOrderNum(String orderNumber){
        connectToDB();
        String sql = "SELECT * FROM order_data WHERE Order_number = \'"+ orderNumber + "\'";
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Date start = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("Order_StartDate"));
            Date order = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("Order_orderDate"));
            Order tempOrder = new Order(rs.getString("Order_number"), rs.getString("Order_ProductKey"), rs.getString("Order_status"), rs.getString("Order_user"), rs.getInt("Order_amount"), start, order);
            return tempOrder;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }finally{
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                return null;
            }
        }
    }

    /**
     * call this method when start running the system, update the favList every 10 mins, auto-set/ auto-remove Favorite object in favList
     * @param productKey
     * @param searchCount
     */
    public static void favManage(String userName, String productKey, int searchCount){
        connectToDB();
        String sql = "SELECT * FROM favorite_data WHERE fav_userName = \'"+ userName + "\'";
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    int minSearch = rs.getInt("fav_search_count");
                    int pos = 0;
                    for(int i = 0; i < 5; i++){
                        if(favList.get(i).getCount() < minSearch){
                            minSearch = favList.get(i).getCount();
                            pos = i;
                        }else{
                            continue;
                        }
                    }
                    if(searchCount > minSearch){
                        Date date = new Date();
                        setFavorite(userName, productKey, searchCount, date);
                        removeFavorite(favList.get(pos).getKey());
                    }
                }
            };
            timer.schedule(task, 10 * 60 * 1000, 10 * 60 * 1000);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally{
            boolean closeStats = closeConnection(stmt);
            if (!closeStats) {
                System.out.println("[ERROR] Fail to close connection to DB.");
            }
        }
    }

    /**
     * set the favProductKey, searchCount, lastSearchDate of the Favorite data structure and add on to the favList
     * @param favProductKey
     * @param searchCount
     * @param lastSearchDate
     */
    private static void setFavorite(String userName, String favProductKey, int searchCount, Date lastSearchDate){
        try {
            Favorite newFav = new Favorite(userName, favProductKey, searchCount, lastSearchDate);
            for(int i = 0; i < favList.size(); i++){
                if(favProductKey.equals(favList.get(i).getKey())){
                    favList.set(i, newFav);
                }else{
                    favList.add(newFav);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * remove favorite from favList
     */
    private static void removeFavorite(String favProductKey) {
            try {
                for (int i = 0; i < favList.size(); i++) {
                    if (favProductKey.equals(favList.get(i).getKey())) {
                        favList.remove(i);
                    } else {
                        System.out.println("[ERROR] Cannot find this favorite record.");
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
    }

    /**
     * get favorite list
     * @return favList
     */
    public static ArrayList<Favorite> getFavorite(){
        return favList;
    }

}