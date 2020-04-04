package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class db {
    public static void connectDB() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        try {
            // db parameters
            String url = "jdbc:sqlite:/Users/james/Documents/GitHub/java-2020-final/src/DB/trip_app.db";
            // create a connection to the database

            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
