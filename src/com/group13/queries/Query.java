package com.group13.queries;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Query {
	
	public static Connection connect() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			return null;
		}

		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/mydb?useSSL=false",
											   "root",
											   "Comp2650!");
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
		
	}
	
	public static String constructQuery(String columnName, String tableName, String whereCondition) {
		
		return String.format("SELECT %s FROM %s WHERE %s", columnName, tableName, whereCondition);
		
	}
	
	public static String constructQuery(String columnName, String tableName) {
		
		return String.format("SELECT %s FROM %s", columnName, tableName);
		
	}
	
	public static String constructInsert(String tableName, String...insertValues) {
		
		if (insertValues.length == 0) {
			throw new IllegalArgumentException("Must have insert values!");
		} else if (insertValues.length % 2 != 0) {
			throw new IllegalArgumentException("Column name to column value not 1:1!");
		}
		
		LinkedList<String> columnNames = new LinkedList<>();
		LinkedList<String> values = new LinkedList<>();
		
		boolean isColumnName = true;
		
		for (String val : insertValues) {
			
			if (isColumnName) {
				
				columnNames.add(val);
				isColumnName = false;
				
			} else {
				
				values.add(val);
				isColumnName = true;
				
			}
			
		}
		
		StringBuilder construct = new StringBuilder("INSERT INTO " + tableName + " (");
		
		for (String columnName : columnNames) {
			
			construct.append(columnName + ", ");
			
		}
		//blah(a,b,c,_
		construct.delete(construct.length() - 2, construct.length());
		
		construct.append(") VALUES (");
		
		for (String value : values) {
			
			construct.append(value + ", ");
			
		}
		
		construct.delete(construct.length() - 2, construct.length());
		
		construct.append(")");
		
		return construct.toString();
		
	}

	public static String constructDelete(String tableName, String...whereConditions) {
		
		if (whereConditions.length == 0) {
			
			throw new IllegalArgumentException("Need at least one WHERE condition!");
			
		}
		
		StringBuilder construct = new StringBuilder("DELETE FROM ");
		
		construct.append(tableName + " WHERE ");
		
		construct.append(whereConditions[0]);
		
		if (whereConditions.length > 1) {
			
			for (int i = 1; i < whereConditions.length; i++) {
				
				construct.append(" AND " + whereConditions[i]);
				
			}
			
		}
		
		return construct.toString();
		
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
			//e.printStackTrace();
			return 0;
		}
		
	}
	
}