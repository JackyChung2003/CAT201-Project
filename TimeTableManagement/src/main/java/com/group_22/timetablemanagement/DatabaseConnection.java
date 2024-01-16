//package com.group_22.timetablemanagement;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DatabaseConnection {
//    public Connection databaseLink;
//
//    public Connection getConnection(){
//        String databaseName = "datahubDEV";
//        String databaseUser = "";       // insert username
//        String databasePassword = "";   // insert password
//        String url = "jdbc:mysql://localhost/" + databaseName;
//
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return databaseLink;
//    }
//}
