package com.group_22.timetablemanagement;

import java.sql.*;

public class JDBCConnection {

    private static Connection connection;

    // Private constructor to prevent instantiation
    private JDBCConnection() {
    }

    // Method to get a database connection
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/jdbc-time-table";
        String username = "root";
        String password = "";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (connection == null) {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                connection = DriverManager.getConnection(url, username, password);
////                Statement statement = connection.createStatement();
////
////                ResultSet resultSet = statement.executeQuery("select * from user");
////
////                while (resultSet.next()) {
////                    System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + resultSet.getString(3) + resultSet.getString(4));
////                }
////
////                connection.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return connection;
    }

    // Method to close the database connection
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    // Method to close the database connection
//    public static void closeConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//                connection = null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
