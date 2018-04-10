package com.group13.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * A creator represents a contributor to the database.
 * <br>
 * <br>To use this class, it must be given a {@link User}. This User
 * must be a Creator, or it will be rejected.
 * 
 * <br>Keep in mind, when creating this, it's more like a LINK to the data. If there's
 * no link (the user doesn't exist or isn't actually a creator), there's no data.
 * 
 * @author Conner Theberge
 */
public class Creator {

	/**
	 * Registers a new creator in the database.
	 * 
	 * @param user The user who is the creator
	 * @param bankRouting The bank routing number for this creator
	 * @param bankAccNumber The bank account number for this creator
	 * @return Whether the registering was successful
	 */
	public static boolean registerCreator(User user, String bankRouting, String bankAccNumber) {
		
		if (!user.isValid()) {
			
			return false;
			
		}
		
		return 0 != Query.executeUpdate(Query.constructInsert("Creator", "CreatorID",
																 Integer.toString(user.getID()),
																 "CreatorTotalPlays",
																 "0",
																 "CreatorBankRouting",
																 String.format("\"%s\"", bankRouting),
																 "CreatorBankAccountNumber",
																 String.format("\"%s\"", bankAccNumber)));
		
		 
	}
	
	/**
	 * Deletes a creator from the database.
	 * <br>
	 * <br>Does NOT delete the user account, just the creator account.
	 * 
	 * @param creator The creator to delete
	 * @return Whether the deletion was successful
	 */
	public static boolean deleteCreator(Creator creator) {
		
		//Delete all created media, delete self.
		
		LinkedList<Media> createdMedia = creator.getCreatedMedia();
		
		for (Media media : createdMedia) {
			
			Media.deleteMedia(media);
			
		}
		
		return 0 != Query.executeUpdate(Query.constructDelete("Creator", "CreatorID = " + creator.getID()));
		
	}
	
	/**
	 * Checks whether the user is a creator
	 */
	public static boolean isCreator(User user) {
		
		if (!user.isValid()) { return false; }
		
		ResultSet set = Query.executeSelect(Query.constructSelect("CreatorID", "Creator", "CreatorID = " + user.getID()));
		
		try {			
			set.next();
			set.getInt("CreatorID");
			
			return !set.wasNull();
			
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	private User user;
	/**
	 * Gets the ID of the creator
	 */
	public int getID() {
		return user.getID();
	}
	
	/**
	 * Constructs a Creator with the given User.
	 * <br>
	 * <br>The given User must be a creator, or it will
	 * be rejected.
	 */
	public Creator(User user) {
		
		if (!isCreator(user)) {
			
			throw new IllegalArgumentException("User is not a creator!");
			
		}
		
		this.user = user;
		
	}
	
	/**
	 * Gets the total number of plays across all of this Creator's
	 * media.
	 */
	public int getTotalPlays() {
		
		LinkedList<Media> allMedia = getCreatedMedia();
		
		int total = 0;
		
		for (Media media : allMedia) {
			
			total += media.getMediaViews();
			
		}
		
		return total;
		
	}
	
	/**
	 * Gets this Creator's bank account routing number.
	 */
	public String getBankRouting() {
		try {
			ResultSet set = Query.executeSelect(Query.constructSelect("CreatorBankRouting", "Creator", "CreatorID = " + user.getID()));
			set.next();
			return set.getString("CreatorBankRouting");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets this Creator's bank account number.
	 */
	public String getBankAccountNumber() {
		try {
			ResultSet set = Query.executeSelect(Query.constructSelect("CreatorBankAccountNumber", "Creator", "CreatorID = " + user.getID()));
			set.next();
			return set.getString("CreatorBankAccountNumber");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets all media created by this Creator
	 */
	public LinkedList<Media> getCreatedMedia() {
		
		LinkedList<Media> media = new LinkedList<>();
		
		ResultSet set = Query.executeSelect(Query.constructSelect("MediaTitle", "Media", "MediaCreatorID = " + user.getID()));
		
		try {
		
			while (set.next()) {
				
				media.add(new Media(set.getString("MediaTitle")));
				
			}
		
		} catch (SQLException e) {
			return media;
		}
		
		return media;
	}
	
	/**
	 * Gets the total dollar payment this Creator should receive
	 * based on the views on their media * PAY_PER_VIEW.
	 */
	public double getPayment() {
		
		return getTotalPlays() * Admin.PAY_PER_VIEW;
		
	}
	
}