package com.group13.queries;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */ 

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * The admin represents the owner of the database and contains functions
 * to get relevant information
 * <br>
 * <br>To use this class, it must be given a {@link User}. This User
 * must be the Admin, or it will be rejected.
 * 
 * <br>Keep in mind, when creating this, it's more like a LINK to the data. If there's
 * no link (the user doesn't exist or isn't actually the admin), there's no data.
 * 
 * @author Conner Theberge
 */
public class Admin {
	
	public static String ADMIN_USERNAME = "admin";
	public static String ADMIN_PASSWORD = "password";
	
	/**
	 * How much the creator should be paid per media view
	 */
	public static final double PAY_PER_VIEW = 0.05;
	
	/**
	 * Checks if the given user is the admin.
	 * <br>This function does not need to look at the database.
	 * 
	 * @param user The user to check
	 */
	public static boolean isAdmin(User user) {
		
		return user.getUsername().equals(ADMIN_USERNAME) && user.getPassword().equals(ADMIN_PASSWORD);
		
	}

	private User user;
	
	/**
	 * Gets the ID of the Admin
	 */
	public int getID() {	
		return user.getID();
	}
	
	/**
	 * Constructs an Admin using the given User.
	 * <br>Throws an IllegalArgumentException if the given user is not the admin.
	 */
	public Admin(User user) {
		
		if (isAdmin(user)) {
			
			this.user = user;
			
		} else {
			
			throw new IllegalArgumentException("User is not admin!");
			
		}
		
	}
	
	/**
	 * Gets all creators in the database (excluding the admin)
	 */
	public LinkedList<Creator> getAllCreators() {
		
		LinkedList<Creator> creators = new LinkedList<>();
		
		ResultSet set = Query.executeSelect("SELECT CreatorID FROM Creator");
		
		try {
			while (set.next()) {
				
				int id = set.getInt("CreatorID");
				
				if (id != this.user.getID()) {
					
					creators.add(new Creator(new User(id)));
					
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return creators;
		
	}
	
	/**
	 * Gets the total payments across all creators.
	 */
	public double getTotalCost() {
		
		double total = 0;
		
		LinkedList<Creator> creators = getAllCreators();
		
		for (Creator creator : creators) {
			
			total += creator.getPayment();
			
		}
		
		return total;
		
	}
	
	/**
	 * Gets all Users currently requesting Creator status
	 */
	public LinkedList<User> getUsersRequestingCreator() {
		
		LinkedList<User> requesters = new LinkedList<>();
		
		ResultSet set = Query.executeSelect(Query.constructSelect("UserID", "User", "UserCreatorStatus = \"" + User.CREATOR_STATUS_WAITING + "\""));
		
		try {
			while (set.next()) {
				
				int id = set.getInt("UserID");
				
				if (id != this.user.getID()) {
					
					requesters.add(new User(id));
					
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return requesters;
		
	}
	
	/**
	 * Rejects the given User's creator status request.
	 */
	public void rejectUserRequest(User user) {
		
		if (user.getCreatorStatus() == User.CREATOR_STATUS_WAITING) {
		
			Query.executeUpdate("UPDATE User SET UserCreatorStatus = \"" + User.CREATOR_STATUS_REJECTED + "\" WHERE UserID = " + user.getID());
		
		}
		
	}
	
	/**
	 * Accepts the User's creator status request.
	 * <br>
	 * <br>Does NOT register them as a Creator. This function
	 * only sets the user's creator status.
	 */
	public void acceptUserRequest() {
		
		if (user.getCreatorStatus() == User.CREATOR_STATUS_WAITING) {
			
			Query.executeUpdate("UPDATE User SET UserCreatorStatus = \"" + User.CREATOR_STATUS_ACCEPTED + "\" WHERE UserID = " + user.getID());
			
		}
		
	}
	
}