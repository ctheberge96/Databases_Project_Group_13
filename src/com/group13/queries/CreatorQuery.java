package com.group13.queries;

import java.util.LinkedList;

public class CreatorQuery {

	/**
	 * Gets the total views across all media created by this creator
	 */
	public int getTotalPlays(int creatorID) {
		//TODO stub
		return 0;
	}
	
	/**
	 * Gets the routing number associated with the creator's account
	 */
	public String getBankRouting(int creatorID) {
		//TODO stub
		return "";
	}
	
	/**
	 * Gets the bank account number associated with this creator's account
	 * 
	 * @param creatorID
	 * @return
	 */
	public String getBankAccountNumber(int creatorID) {
		//TODO stub
		return "";
	}
	
	/**
	 * Gets a list of Media IDs that this creator has created
	 * 
	 * @param creatorID
	 * @return
	 */
	public LinkedList<Integer> getCreatedMedia(int creatorID) {
		//TODO stub
		return null;
	}
	
}