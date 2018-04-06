package com.group13.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserQuery {

	/**
	 * Gets all media favorited by the given user
	 * 
	 * @param userId The ID associated with the user
	 * @return A list of IDs associated with Media that was favorited with the user
	 */
	public static LinkedList<Integer> getFavoritedMedia(int userId) {
		
		ResultSet set = Query.executeSelect("SELECT FK_MediaID FROM FavoritedMedia WHERE FK_UserID = " + userId);
		
		int count = 1;
		
		try {
		
			while(set.next()) {
				
				System.out.println(set.getInt(count));
				count++;
				
			}
		
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	/**
	 * Favorites certain media for a certain user
	 * 
	 * @param userID ID of the user
	 * @param mediaID ID of the media to favorite
	 * @return whether the favoriting was successful
	 */
	public static boolean favoriteMedia(int userID, int mediaID) {
		
		int result = Query.executeUpdate(String.format("INSERT INTO FavoritedMedia (FK_MediaID, FK_UserID)\r\n" + 
													   "VALUES (%d, %d);",
													   mediaID,userID));
		
		return result != 0;
		
	}
	
	/**
	 * Unfavorites certain media for a certain user
	 * 
	 * @param userID ID of the user
	 * @param mediaID ID of the media to unfavorite
	 * @return whether the unfavoriting was successful
	 */
	public static boolean unfavoriteMedia(int userID, int mediaID) {
		
		int result = Query.executeUpdate(String.format("DELETE FROM FavoritedMedia WHERE FK_MediaID = %d AND FK_UserID = %d",
										 mediaID,userID));
		
		return result != 0;
		
	}
	
	public LinkedList<String> getFollowedTags(int userId) {
		//TODO stub
		return null;
	}
	
	public void followTag(int userId, String tag) {
		//TODO stub
	}
	
	public void unFollowTag(int userID, String tag) {
		
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
			
			return result != 0;
		
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