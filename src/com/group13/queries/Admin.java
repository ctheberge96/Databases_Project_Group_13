package com.group13.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Admin {
	
	public final static String ADMIN_USERNAME = "admin";
	public final static String ADMIN_PASSWORD = "password";
	
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
	
	public Admin(User user) {
		
		if (isAdmin(user)) {
			
			this.user = user;
			
		} else {
			
			throw new IllegalArgumentException("User is not admin!");
			
		}
		
	}
	
	public LinkedList<User> getAllCreators() {
		
		LinkedList<User> creators = new LinkedList<>();
		
		ResultSet set = Query.executeSelect("SELECT CreatorID FROM Creator");
		
		int ind = 1;
		
		try {
			while (set.next()) {
				
				creators.add(new User(set.getInt(ind)));
				ind++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return creators;
		
	}
	
	public double getTotalCost() {
		
		
		
		return 0.0;
		
	}
	
	public double getCreatorPayment(int creatorId) {
		return 0.0;
	}
	
}