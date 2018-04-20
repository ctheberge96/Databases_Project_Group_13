package com.group13.queries;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Query contains methods to allow for executing queries to the database
 * 
 * @author Conner Theberge
 */
public class Query {
	
	/**
	 * Connects to the database and returns the {@link Connection}.
	 * <br>Returns null if there is any error connecting
	 */
	private static Connection connect() {
		
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
	
	/**
	 * Constructs a select statement.
	 * <br>Use {@link #executeSelect(String)} to execute
	 * the constructed statement.
	 * 
	 * @param columnName The name of the column to select
	 * @param tableName The table to select from
	 * @param whereCondition The where statement for specific selection
	 * (Ex: ID = selectID)
	 * @return The constructed statement as a string
	 */
	public static String constructSelect(String columnName, String tableName, String whereCondition) {
		
		return String.format("SELECT %s FROM %s WHERE %s", columnName, tableName, whereCondition);
		
	}
	
	/**
	 * Constructs a select statement.
	 * <br>Use {@link #executeSelect(String)} to execute
	 * the constructed statement.
	 * 
	 * @param columnName The name of the column to select
	 * @param tableName The table to select from
	 * @return The constructed statement as a string
	 */
	public static String constructSelect(String columnName, String tableName) {
		
		return String.format("SELECT %s FROM %s", columnName, tableName);
		
	}
	
	/**
	 * Constructs an insert statement.
	 * <br>Use {@link #executeUpdate(String)} to execute the constructed statement
	 * 
	 * @param tableName The name of the table to insert into
	 * @param insertValues The values to insert.
	 * <br>Values are structured 1:1 with column name first,
	 * and actual value in the next argument
	 * @return The constructed statement as a string
	 */
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

	/**
	 * Constructs a delete statement
	 * <br>Use {@link #executeUpdate(String)} to execute the constructed statement
	 * 
	 * @param tableName The name of the table to delete from
	 * @param whereConditions The where statements for specific deletion
	 * (Ex: ID = selectID)
	 * @return The constructed statement
	 */
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
	
	/**
	 * Executes a select statement.
	 * <br>Cannot execute any statements that would update the database.
	 * 
	 * @param query The query statement to execute
	 * @return The {@link ResultSet} resulting from the select. Null on error.
	 */
	public static ResultSet executeSelect(String query) {
		
		try {
			Connection connection = connect();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE mydb;");
			return statement.executeQuery(query);
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Executes an update statement.
	 * <br>Cannot execute any statements that would select from the database.
	 * 
	 * @param query The query statement to execute
	 * @return The number of rows in the database affected.
	 */
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