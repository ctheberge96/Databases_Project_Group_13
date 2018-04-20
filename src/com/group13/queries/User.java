package com.group13.queries;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A User represents any user, regardless of rank.
 * <br>
 * <br>The User is the base class of Admin and Creator.
 * 
 * @author Conner Theberge
 */
public class User {
	
	public static final char CREATOR_STATUS_NONE = 'n';
	public static final char CREATOR_STATUS_WAITING = 'w';
	public static final char CREATOR_STATUS_REJECTED = 'r';
	public static final char CREATOR_STATUS_ACCEPTED = 'a';
	
	private String username, password;
	private int id;
	public int getID() {
		return id;
	}

	/**
	 * Registers a new User (Adds them to the database)
	 * 
	 * @param username The username of the new User
	 * @param password The password of the new User
	 * @return The ID of the new User
	 */
	public static int registerNewUser(String username, String password) {
		
		if (username.equals(password)) {
			
			throw new IllegalArgumentException("Username and password cannot be equal!");
			
		}
		
		return Query.executeUpdate(Query.constructInsert("User", "UserName",
																 String.format("\"%s\"", username),
																 "UserPassword",
																 String.format("\"%s\"", password),
																 "UserCreatorStatus",
																 String.format("\"%s\"", CREATOR_STATUS_NONE)));
		
	}
	
	/**
	 * Deletes this User from the database (deleting their favorited media and followed tag entities)
	 */
	public static boolean deleteUser(User user) {
		
		if (user.isValid()) {
			
			if(Creator.isCreator(user)) {
				
				Creator.deleteCreator(new Creator(user));
				
			}
	
			Query.executeUpdate(String.format("DELETE FROM FollowedTag WHERE FK_FM_UserID = %d", user.id));
			Query.executeUpdate(String.format("DELETE FROM FavoritedMedia WHERE FK_FT_UserID = %d", user.id));
			Query.executeUpdate(String.format("DELETE FROM User WHERE UserID = %d", user.id));
		
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Checks to see if the given username exists in the database
	 */
	public static boolean usernameExists(String username) {
		
		ResultSet set = Query.executeSelect(Query.constructSelect("UserID", "User", "UserName = \"" + username + "\""));
		
		try {
			set.next();
			set.getInt("UserID");
			return true;
		} catch (Exception e) {
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
			ResultSet set = Query.executeSelect(String.format("SELECT UserID FROM User WHERE UserName = \"%s\" AND UserPassword = \"%s\"", username, password));
			set.next();
			this.id = set.getInt("UserID");
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
	 * <br>
	 * <br>If this user does not exist, its ID is set to -1, and
	 * its username and password are set to empty strings.
	 * @param id The ID of the user
	 */
	public User(int id) {
		
		ResultSet set = Query.executeSelect(String.format("SELECT UserName, UserPassword FROM User WHERE UserID = %d", id));
		this.id = id;
		
		try {
			set.next();
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
	public boolean changeUsername(String newUsername) {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		int result = Query.executeUpdate("UPDATE User SET UserName = \"" + newUsername + "\" WHERE UserID = " + this.id);
		
		if (result != 0) {
		
			this.username = newUsername;
			return true;
		
		} else {
			
			return false;
			
		}
		
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
	public boolean changePassword(String newPassword) {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		int result = Query.executeUpdate("UPDATE User SET UserPassword = \"" + newPassword + "\" WHERE UserID = " + this.id);
		
		if (result != 0) {
			
			this.password = newPassword;
			return true;
		
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Requests Creator status from the Admin.
	 * <br>This is not instant, and will require the Admin to log in and
	 * view the request later.
	 */
	public boolean requestCreatorStatus() {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		if (getCreatorStatus() != CREATOR_STATUS_ACCEPTED) {
		
			int result = Query.executeUpdate("UPDATE User SET UserCreatorStatus = \"" + CREATOR_STATUS_WAITING + "\" WHERE UserID = " + this.id);
		
			return result != 0;
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Resets this User's creator status to none
	 * 
	 * @return If the change was successful
	 */
	public boolean resetCreatorStatus() {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		int result = Query.executeUpdate("UPDATE User SET UserCreatorStatus = \"" + CREATOR_STATUS_NONE + "\" WHERE UserID = " + this.id);
		
		return result != 0;
		
	}
	
	/**
	 * Gets the status of this User's creator status request
	 */
	public char getCreatorStatus() {
		
		if (!isValid()) {
			throw new IllegalStateException("User is not valid! (Doesn't exist!) Check for validity using isValid()!");
		}
		
		ResultSet set = Query.executeSelect(Query.constructSelect("UserCreatorStatus", "User", "UserID = " + this.id));
		try {
			set.next();
			return set.getString("UserCreatorStatus").toCharArray()[0];
		} catch (SQLException e) {
			return CREATOR_STATUS_NONE;
		}
		
	}
	
}