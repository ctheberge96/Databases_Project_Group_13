package com.group13.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Creator {

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
	
	public static boolean deleteCreator(User user) {
		
		if (!isCreator(user)) {
			
			return false;
			
		} else {
			
			return deleteCreator(new Creator(user));
			
		}
		
	}
	
	public static boolean deleteCreator(Creator creator) {
		
		//Delete all created media, delete self.
		
		LinkedList<Media> createdMedia = creator.getCreatedMedia();
		
		for (Media media : createdMedia) {
			
			Media.deleteMedia(media);
			
		}
		
		return 0 != Query.executeUpdate(Query.constructDelete("Creator", "CreatorID = " + creator.getID()));
		
	}
	
	public static boolean isCreator(User user) {
		
		if (!user.isValid()) { return false; }
		
		ResultSet set = Query.executeSelect(Query.constructQuery("CreatorID", "Creator", "CreatorID = " + user.getID()));
		
		try {			
			set.next();
			set.getInt("CreatorID");
			
			return !set.wasNull();
			
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	private User user;
	public int getID() {
		return user.getID();
	}
	
	public Creator(User user) {
		
		if (!isCreator(user)) {
			
			throw new IllegalArgumentException("User is not a creator!");
			
		}
		
		this.user = user;
		
	}
	
	public int getTotalPlays() {
		
		LinkedList<Media> allMedia = getCreatedMedia();
		
		int total = 0;
		
		for (Media media : allMedia) {
			
			total += media.getMediaViews();
			
		}
		
		return total;
		
	}
	
	public String getBankRouting() {
		try {
			ResultSet set = Query.executeSelect(Query.constructQuery("CreatorBankRouting", "Creator", "CreatorID = " + user.getID()));
			set.next();
			return set.getString("CreatorBankRouting");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getBankAccountNumber() {
		try {
			ResultSet set = Query.executeSelect(Query.constructQuery("CreatorBankAccountNumber", "Creator", "CreatorID = " + user.getID()));
			set.next();
			return set.getString("CreatorBankAccountNumber");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<Media> getCreatedMedia() {
		
		LinkedList<Media> media = new LinkedList<>();
		
		ResultSet set = Query.executeSelect(Query.constructQuery("MediaID", "Media", "MediaCreatorID = " + user.getID()));
		
		try {
		
			while (set.next()) {
				
				media.add(new Media(set.getInt("MediaID")));
				
			}
		
		} catch (SQLException e) {
			return media;
		}
		
		return media;
	}
	
}