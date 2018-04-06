package com.group13.queries;

import java.util.LinkedList;

public class CreatorQuery {

	public static boolean registerCreator(String username, String password, String bankRouting, String bankAccNumber) {
		
		//int userID = UserQuery.checkLogin(username, password);
		
		 //Query.executeUpdate("INSERT INTO Creator (CreatorID, CreatorTotalPlays, CreatorBankRouting, CreatorBankAccountNumber)"
		//		+ " VALUES (" + userID + "0,0,0);");
		 
		 return true;
		 
	}
	
	public int getTotalPlays(int creatorID) {
		//TODO stub
		return 0;
	}
	
	public String getBankRouting(int creatorID) {
		//TODO stub
		return "";
	}
	
	public String getBankAccountNumber(int creatorID) {
		//TODO stub
		return "";
	}
	
	public LinkedList<Integer> getCreatedMedia(int creatorID) {
		//TODO stub
		return null;
	}
	
}