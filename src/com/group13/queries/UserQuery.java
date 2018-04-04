package com.group13.queries;

import java.io.File;
import java.util.LinkedList;

/*
 * Possibly contains static methods for querying for
 * obtaining stuff from the user table,
 * such as password, username, etc.
 */
public class UserQuery {

	/**
	 * Gets all media favorited by the user with the given ID
	 * 
	 * @return The IDs of all media followed by the user with the given ID
	 */
	public LinkedList<Integer> getFavoritedMedia(int userId) {
		//TODO stub
		return null;
	}
	
	/**
	 * Favorites media for the user with the given ID.
	 * 
	 * @param userId The ID of the user
	 * @param mediaID The ID of the favorited media
	 */
	public void favoriteMedia(int userId, int mediaID) {
		//TODO stub
	}
	
	/**
	 * Gets all tags followed by the user with the given ID
	 * 
	 * @return All tags followed by the user with the given ID
	 */
	public LinkedList<String> getFollowedTags(int userId) {
		//TODO stub
		return null;
	}
	
	/**
	 * Follows a tag for the user with the given ID.
	 * 
	 * @param userId The ID of the user
	 * @param tag The name of the tag
	 */
	public void followTag(int userId, String tag) {
		//TODO stub
	}
	
	/**
	 * Gets the userID associated with the given username and password.
	 *
	 * @return The ID of the user with the given username and password.
	 * -1 if there's no user associated with the given login info
	 */
	public int checkLogin(String username, String password) {
		//TODO stub
		return -1;
	}
	
	/**
	 * Registers the new user.
	 * <br>Will fail if username is already registered.
	 * 
	 * @param username The username of the new user
	 * @param password The password of the new user
	 */
	public void registerUser(String username, String password) {
		//TODO stub
	}
	
}