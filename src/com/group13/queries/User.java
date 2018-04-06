package com.group13.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	
	private String username, password;
	private int id;

	/**
	 * Registers a new User (Adds them to the database)
	 * 
	 * @param username The username of the new User
	 * @param password The password of the new User
	 * @return The ID of the new User
	 */
	public static int registerNewUser(String username, String password) {
		
		try {
			Query.executeUpdate(String.format("INSERT INTO User (UserName, UserPassword) VALUES (\"%s\", \"%s\")",username,password));
			return Query.executeSelect(String.format("SELECT UserID WHERE UserName = \"%s\" AND UserPassword = \"%s\"", username, password)).getInt("UserID");
		} catch (SQLException e) {
			return -1;
		}
		
	}
	
	/**
	 * Deletes this User from the database (deleting their favorited media and followed tag entities)
	 */
	public static boolean deleteUser(User user) {
		
		if (user.isValid()) {
	
			Query.executeUpdate(String.format("DELETE FollowedTag WHERE FK_FM_UserID = %d", user.id));
			Query.executeUpdate(String.format("DELETE FavoritedMedia WHERE FK_FT_UserID = %d", user.id));
			Query.executeUpdate(String.format("DELETE User WHERE UserID = %d", user.id));
		
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Constructs a User based on the given username and password.
	 * <br>Should be used when User ID is unknown
	 * 
	 * @param username The username of the user
	 * @param password The password of the user
	 */
	public User(String username, String password) {
		
		try {
			this.id = Query.executeSelect(String.format("SELECT UserID WHERE UserName = \"%s\" AND UserPassword = \"%s\"", username, password)).getInt("UserID");
			this.username = username;
			this.password = password;
		} catch (SQLException e) {
			this.id = -1;
			this.username = "";
			this.password = "";
		}
		
	}
	
	/**
	 * Constructs a User based on ID.
	 * 
	 * @param id The ID of the user
	 */
	public User(int id) {
		
		ResultSet set = Query.executeSelect(String.format("SELECT UserName, UserPassword WHERE UserID = %d", id));
		
		this.id = id;
		
		try {
			this.username = set.getString("UserName");
			this.password = set.getString("UserPassword");
		} catch (SQLException e) {
			this.id = -1;
			this.username = "";
			this.password = "";
		}
		
	}
	
	/**
	 * Checks if this User is valid (exists)
	 */
	public boolean isValid() {
		
		return id != -1;
		
	}
	
	/**
	 * Returns the username of this User.
	 */
	public String getUsername() {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		return username;
		
	}
	
	/**
	 * Changes this User's username
	 */
	public void changeUsername(String newUsername) {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		Query.executeUpdate("UPDATE User SET UserName = \"" + newUsername + "\" WHERE UserID = " + this.id);
		
		this.username = newUsername;
		
	}
	
	/**
	 * Returns the password of this User.
	 */
	public String getPassword() {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		return password;
		
	}
	
	/**
	 * Changes this User's password
	 */
	public void changePassword(String newPassword) {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		Query.executeUpdate("UPDATE User SET UserPassword = \"" + newPassword + "\" WHERE UserID = " + this.id);
		
		this.password = newPassword;
		
	}
	
}