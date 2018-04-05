package com.group13.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserQuery {

	public LinkedList<Integer> getFavoritedMedia(int userId) {
		//TODO stub
		return null;
	}
	
	public void favoriteMedia(int userId, int mediaID) {
		//TODO stub
	}
	
	public void unfavoriteMedia(int userId, int mediaID) {
		//TODO stub
	}
	
	public LinkedList<String> getFollowedTags(int userId) {
		//TODO stub
		return null;
	}
	
	public void followTag(int userId, String tag) {
		//TODO stub
	}
	
	public void unFollowTag(int userID, String tag) {
		//TODO stub
	}

	/**
	 * Gets the ID of the user associated with this login
	 * <br>-1 if no user has this login
	 * 
	 * @param username
	 * @param password
	 */
	public static int checkLogin(String username, String password) {
		ResultSet set = Query.executeSelect("SELECT UserID FROM user WHERE UserName = \"" + username + "\" AND UserPassword = \"" + password + "\"");
		try {
			set.next();
			return set.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Registers this user's login into the database.
	 * 
	 * @param username
	 * @param password
	 * @return True if the register was successful. False otherwise.
	 * <br>False can occur when there is already a user associated with the given login
	 */
	public static  boolean registerUser(String username, String password) {
		
		if (checkLogin(username, password) == -1) {
		
			int result = Query.executeUpdate(String.format("INSERT INTO user (UserName, UserPassword)\r\n" + 
														"VALUES (\"%s\", \"%s\");",
														username, password));
			
			return (result == 0 ? false:true);
		
		} else {
			return false;
		}
		
	}

	/**
	 * Deletes this user from the database.
	 * <br>Deleting a user deletes all corresponding data
	 * relating to this user, including if they were a creator.
	 * 
	 * @param id The ID of the user to delete
	 * @return Whether the deletion was successful
	 */
	public static boolean deleteUser(int id) {

		int result = Query.executeUpdate("DELETE FROM user WHERE UserID = \"" + id + "\"");
		
		return (result == 0 ? false:true);
		
	}
	
}