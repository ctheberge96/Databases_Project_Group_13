package com.group13.queries;

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

	public int checkLogin(String username, String password) {
		//TODO stub
		return -1;
	}
	
	public void registerUser(String username, String password) {
		//TODO stub
	}
	
	public void deleteUser(String userID) {
		//TODO stub
	}
	
}