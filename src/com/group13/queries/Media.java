package com.group13.queries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * A class that represents a Media entity
 * 
 * @author Conner Theberge
 *
 */
public class Media {
	
	public enum MediaType { VIDEO, MUSIC, IMAGE, UNKNOWN }
	
	/**
	 * Adds media to the database.
	 * 
	 * @param creatorID The ID of the creator
	 * @param mediaTitle The title of the media
	 * @param type The type of media
	 * @param mediaFile The media file
	 * 
	 * @return Whether the addition was successful
	 */
	public static boolean addMedia(int creatorID,
								   String mediaTitle,
								   MediaType type,
								   File mediaFile)
	{
		
		try ( Socket server = new Socket("localhost",1);
				DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
				DataInputStream fromServer = new DataInputStream(server.getInputStream()); )
		{
			
			toServer.writeUTF("addfile~" + mediaFile.getName());
			
			if (fromServer.readUTF().equals("OKAY")) {
				
				int read = 0;
				byte[] buff = new byte[1024];
				try (FileInputStream fromFile = new FileInputStream(mediaFile);) {
					
					while ( ( read = fromFile.read(buff) ) != -1 ) {
						
						toServer.write(buff, 0, read);
						
					}
					
				} catch (IOException e) {
					
					e.printStackTrace();
					return false;
					
				}
			
			} else {
				
				return false;
				
			}
			
		} catch(Exception e) {
			
			return false;
			
		}
			
		Calendar calendar = Calendar.getInstance();
		
		int result = Query.executeUpdate(String.format( "INSERT INTO Media (MediaTitle, MediaType, MediaFileName, MediaLastOpened, MediaDateCreated, MediaCreatorID, MediaViews)"
													  + "VALUES ( %s, %s, %s, %s, %s, %d, %d )",
													  mediaTitle,
													  mediaFile.getName(),
													  new Date(calendar.getTimeInMillis()),
													  new Date(calendar.getTimeInMillis()),
													  creatorID,
													  0));
		
		return result != 0;
		
	}
	
	/**
	 * Deletes a given media from the database
	 * 
	 * @param media The media entity to delete
	 * @return Whether the deletion was successful
	 */
	public static boolean deleteMedia(Media media) {
		
		Query.executeUpdate(String.format("DELETE TaggedMedia WHERE FK_FT_MediaID = %d", media.id));
		Query.executeUpdate(String.format("DELETE FavoritedMedia WHERE FK_FT_MediaID = %d", media.id));
		return Query.executeUpdate(String.format("DELETE Media WHERE MediaID = %d", media.id)) != 0;
		
	}
	
	private int id;
	
	/**
	 * Creates a media entity with the given ID
	 */
	public Media(int mediaID) {
		
		id = mediaID;
		
	}
	
	/**
	 * Returns the type of media this entity represents
	 */
	public MediaType getMediaType() {
		
		String type;
		try {
			type = Query.executeSelect("SELECT MediaType FROM Media WHERE MediaID = " + id).getString("MediaType");
		} catch (SQLException e) {
			return MediaType.UNKNOWN;
		}
		
		if (type.equals("v")) {
			return MediaType.VIDEO;
		} else if (type.equals("m")) {
			return MediaType.MUSIC;
		} else if (type.equals("i")) {
			return MediaType.IMAGE;
		} else {
			return MediaType.UNKNOWN;
		}
		
	}
	
	/**
	 * Returns the media file associated with this media entity
	 * <br>This function can take time depending on the size of the file.
	 * Be sure to inform the user.
	 */
	public File getMediaFile() {
		
		String filePath;
		try {
			filePath = Query.executeSelect("SELECT MediaFileName FROM Media WHERE MediaID = " + id).getString("MediaFileName");
		} catch (SQLException e2) {
			return null;
		}
		
		File file = new File(filePath);
		
		try ( Socket server = new Socket("localhost",1);
				DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
				DataInputStream fromServer = new DataInputStream(server.getInputStream()); )
		{
			
			toServer.writeUTF("getfile~" + filePath);
			
			if (fromServer.readUTF().equals("OKAY")) {
				
				int read = 0;
				byte[] buff = new byte[1024];
				try (FileOutputStream toFile = new FileOutputStream(file);) {
					
					while ( ( read = fromServer.read(buff) ) != -1 ) {
						
						toFile.write(buff, 0, read);
						
					}
					
				} catch (IOException e) {
					
					e.printStackTrace();
					return null;
					
				}
				
				return file;
			
			} else {
				
				return null;
				
			}
			
		} catch (IOException e1) {
			return null;
		}
		
	}
	
	/**
	 * Returns the date that this media was last opened
	 */
	public Date getLastOpened() {
		try {
			return Query.executeSelect("SELECT MediaLastOpened FROM Media WHERE MediaID = " + id).getDate("MediaLastOpened");
		} catch (SQLException e) {
			return null;
		}
	}
	/**
	 * Sets the date that this media was last opened to today
	 */
	public void setLastOpened() {
		Calendar calendar = Calendar.getInstance();
		Query.executeUpdate("UPDATE Media SET MediaLastOpened = " + new Date(calendar.getTimeInMillis()) + " WHERE MediaID = " + this.id);
	}
	
	/**
	 * Gets the date that this media was created
	 */
	public Date getDateCreated() {
		try {
			return Query.executeSelect("SELECT MediaDateCreated FROM Media WHERE MediaID = " + id).getDate("MediaDateCreated");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Gets the ID of the creator that created this media
	 */
	public int getCreatorID() {
		
		try {
			return Query.executeSelect("SELECT CreatorID FROM Media WHERE MediaID = " + id).getInt("CreatorID");
		} catch (SQLException e) {
			return -1;
		}
		
	}
	
	/**
	 * Returns the amount of views this media has
	 */
	public int getMediaViews() {
		
		try {
			return Query.executeSelect("SELECT MediaViews FROM Media WHERE MediaID = " + id).getInt("MediaViews");
		} catch (SQLException e) {
			return -1;
		}
		
	}
	/**
	 * Adds a single view to the amount of media views
	 */
	public void addView() {
		
		try {
			
			int oldViews = Query.executeSelect("SELECT MediaViews WHERE MediaID = " + id).getInt("MediaViews");
			Query.executeUpdate("UPDATE Media SET MediaViews = " + (oldViews + 1) + " WHERE UserID = " + this.id);
			
		} catch (SQLException e) {}
		
	}
	
}