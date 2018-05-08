package com.jarroba.scraping.Scraping_jarroba_java;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL_Test_Remoto {
	
	    public static void main(String[] args) {
	        Connection conn = null;

	        try {
	            String userName = "tuwebroc_scrapu";
	            String password = "rojoo85883606";

	            String url = "jdbc:mysql://186.64.119.120:3306/tuwebroc_scrap";
	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	            conn = DriverManager.getConnection(url, userName, password);
	            System.out.println("Database connection established");
	        } catch (Exception e) {
	            System.err.println("Cannot connect to database server");
	            System.err.println(e.getMessage());
	            e.printStackTrace();
	        } finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                    System.out.println("Database Connection Terminated");
	                } catch (Exception e) {}
	            }
	        }
	    }
}
