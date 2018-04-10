package com.group13.queries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * A class that represents a Media entity
 * 
 * @author Conner Theberge
 *
 */
public class Media {
	
	public static final char TYPE_VIDEO = 'v';
	public static final char TYPE_IMAGE = 'i';
	public static final char TYPE_MUSIC = 'm';
	public static final char TYPE_UNKNOWN = '?';
	
	/**
	 * Translates the mediaType from a char to a readable string.
	 * <br>This is what should be displayed to the user.
	 * 
	 * @param mediaType The type as an identifying char
	 * @return The string identifier version of the given type
	 */
	public static final String translateMediaType(char mediaType) {
		
		switch(mediaType) {
		
		case 'v':
			return "Video";
		case 'i':
			return "Image";
		case 'm':
			return "Music";
		default:
			return "Uknown";
		}
		
	}

	/**
	 * Adds media to the database.
	 * <br>This may take time, as this function
	 * transfers the whole file to the file server.
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
								   char type,
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
					
					return false;
					
				}
			
			} else {
				
				return false;
				
			}
			
		} catch(Exception e) {
			
			return false;
			
		}
			
		Calendar calendar = Calendar.getInstance();
		
		int result = Query.executeUpdate(Query.constructInsert("Media", "MediaTitle",
																		String.format("\"%s\"", mediaTitle),
																		"MediaType",
																		String.format("\"%s\"", String.valueOf(type)),
																		"MediaFileName",
																		String.format("\"%s\"", mediaFile.getName()),
																		"MediaLastOpened",
																		String.format("\"%s\"", new Date(calendar.getTimeInMillis()).toString()),
																		"MediaDateCreated",
																		String.format("\"%s\"", new Date(calendar.getTimeInMillis()).toString()),
																		"MediaCreatorID",
																		Integer.toString(creatorID),
																		"MediaViews",
																		"0"));
		
		if (result != 0) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Deletes a given media from the database
	 * 
	 * <br>This may take time, as this function
	 * talks to the file server.
	 * 
	 * @param media The media entity to delete
	 * @return Whether the deletion was successful
	 */
	public static boolean deleteMedia(Media media) {
		
		try ( Socket server = new Socket("localhost",1);
				DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
				DataInputStream fromServer = new DataInputStream(server.getInputStream()); )
		{
			
			toServer.writeUTF("deletefile~" + media.getMediaFileName());
			
			if (fromServer.readUTF().equals("OKAY")) {
				
				Query.executeUpdate(String.format("DELETE FROM TaggedMedia WHERE FK_FT_MediaTitle = \"%s\"", media.title));
				Query.executeUpdate(String.format("DELETE FROM FavoritedMedia WHERE FK_FT_MediaTitle = \"%s\"", media.title));
				int result = Query.executeUpdate(Query.constructDelete("Media", "MediaTitle = \"" + media.title + "\""));
				
				return result != 0;
				
			} else {
				
				return false;
				
			}
			
		} catch(IOException e) {

			return false;
			
		}
		
	}
	
	private String title;
	
	/**
	 * Gets the title of this Media.
	 * <br>Title is the identifying feature of Media
	 */
	public String getMediaTitle() {
		
		return title;
		
	}
	
	/**
	 * Creates a media entity with the given title
	 * <br>This is like a link to the information in the database.
	 * <br>If this title doesn't exist in the database, there's no data to get.
	 */
	public Media(String mediaTitle) {
		
		if (mediaTitle == null) {
			
			throw new IllegalArgumentException("MediaTitle cannot be null!");
			
		}
		
		title = mediaTitle;
		
	}
	
	/**
	 * Returns the type of media this entity represents
	 */
	public String getMediaType() {
		
		try {
			ResultSet set = Query.executeSelect("SELECT MediaType FROM Media WHERE MediaTitle = \"" + title + "\"");
			set.next();
			return translateMediaType(set.getString("MediaType").charAt(0));
		} catch (SQLException e) {
			return translateMediaType(TYPE_UNKNOWN);
		}
		
	}
	
	/**
	 * Gets the file name for this media.
	 * <br>Ex: media.mp4
	 */
	public String getMediaFileName() {
		
		try {
			ResultSet set = Query.executeSelect("SELECT MediaFileName FROM Media WHERE MediaTitle = \"" + title + "\"");
			set.next();
			return set.getString("MediaFileName");
		} catch (SQLException e2) {
			return null;
		}
		
	}
	
	/**
	 * Returns the media file associated with this media entity
	 * <br>This function can take time depending on the size of the file.
	 * Be sure to inform the user.
	 */
	public File getMediaFile() {
		
		String filePath = getMediaFileName();
		
		if (filePath == null) {
			
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
			ResultSet set = Query.executeSelect("SELECT MediaLastOpened FROM Media WHERE MediaTitle = \"" + title + "\"");
			set.next();
			return set.getDate("MediaLastOpened");
		} catch (SQLException e) {
			return null;
		}
	}
	/**
	 * Sets the date that this media was last opened to today
	 */
	public void setLastOpened() {
		Calendar calendar = Calendar.getInstance();
		Query.executeUpdate("UPDATE Media SET MediaLastOpened = " + new Date(calendar.getTimeInMillis()) + " WHERE MediaTitle = " + this.title);
	}
	
	/**
	 * Gets the date that this media was created
	 */
	public Date getDateCreated() {
		try {
			ResultSet set = Query.executeSelect("SELECT MediaDateCreated FROM Media WHERE MediaTitle = \"" + title + "\"");
			set.next();
			return set.getDate("MediaDateCreated");
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Gets the ID of the creator that created this media
	 */
	public int getCreatorID() {
		
		try {
			ResultSet set = Query.executeSelect("SELECT MediaCreatorID FROM Media WHERE MediaTitle = \"" + title + "\""); 
			set.next();
			return set.getInt("MediaCreatorID");
		} catch (SQLException e) {
			//e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * Returns the amount of views this media has
	 */
	public int getMediaViews() {
		
		try {
			ResultSet set = Query.executeSelect("SELECT MediaViews FROM Media WHERE MediaTitle = \"" + title + "\""); 
			set.next();
			return set.getInt("MediaViews");
		} catch (SQLException e) {
			return -1;
		}
		
	}
	/**
	 * Adds a single view to the amount of media views
	 */
	public void addView() {
		
		int oldViews = getMediaViews();
		Query.executeUpdate("UPDATE Media SET MediaViews = " + (oldViews + 1) + " WHERE MediaTitle = \"" + title + "\"");
		
	}
	
	/**
	 * Gets all media from the database, regardless of who created it.
	 */
	public static LinkedList<Media> getAllMedia() {
		
		ResultSet set = Query.executeSelect(Query.constructSelect("MediaTitle", "Media"));
		
		LinkedList<Media> mediaList = new LinkedList<Media>();
		
		try {
			
			while (set.next()) {
			
				mediaList.add(new Media(set.getString("MediaTitle")));
				
			}
		
		} catch (SQLException e) { }
		
		return mediaList;
		
	}
	
}