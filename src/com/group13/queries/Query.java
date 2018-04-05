package com.group13.queries;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {
	
	public static Connection connect() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			return null;
		}

		try {
			return DriverManager.getConnection("jdbc:mysql://localhost",
											   "root",
											   "Comp2650!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static ResultSet executeSelect(String query) {
		
		try {
			Connection connection = connect();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE mydb;");
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static int executeUpdate(String query) {
		
		try {
			Connection connection = connect();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE mydb;");
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
}