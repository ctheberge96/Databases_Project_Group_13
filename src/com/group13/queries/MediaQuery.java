package com.group13.queries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;

public class MediaQuery {
	
	public enum MediaType { VIDEO, MUSIC, IMAGE }
	
	public static boolean addMedia(String mediaTitle,
							MediaType type,
							File mediaFile,
							int creatorID)
	{
		
		Connection connection = Query.connect();
		
		try(Socket fileServerConnection = new Socket("localhost",1); DataOutputStream toServer = new DataOutputStream(fileServerConnection.getOutputStream());
			DataInputStream fromServer = new DataInputStream(fileServerConnection.getInputStream());) {
			
			PreparedStatement statement = connection.prepareStatement("USE mydb;\r\nINSERT INTO Media (MediaTitle, "
																						+ "MediaType, "
																						+ "MediaFileName, "
																						+ "MediaLastOpened, "
																						+ "MediaDateCreated, "
																						+ "MediaCreatorID, "
																						+ "MediaViews)"
																						+ "\r\nVALUES (?, ?, ?, ?, ?, ?, ?);");
			System.out.println("hey");
			statement.setString(1, mediaTitle);
			switch(type) {
			case IMAGE:
				statement.setString(2, "i");
				break;
			case MUSIC:
				statement.setString(2, "m");
				break;
			case VIDEO:
				statement.setString(2, "v");
				break;
			default:
				throw new IllegalArgumentException("MediaType must be of type IMAGE, MUSIC, or VIDEO");
			}
			statement.setString(3, mediaFile.getName());
			statement.setNull(4, java.sql.Types.DATE);
			Calendar calendar = Calendar.getInstance();
			statement.setDate(5, new Date(calendar.getTimeInMillis()));
			statement.setInt(6, creatorID);
			statement.setInt(7, 0);
			System.out.println("hey");
			toServer.writeUTF("addfile~"+mediaFile.getName());
			String response = fromServer.readUTF();
			if (response.equals("OKAY")) {
				
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
			
			System.out.println(statement.toString());
			
			int result = statement.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return false;
		
	}

	public LinkedList<Integer> getAllMedia() {
	
		LinkedList<Integer> mediaIDs = new LinkedList<>();
		
		ResultSet set = Query.executeSelect("SELECT MediaID From Media");
		int count = 1;
		try {
			while (set.next()) {
				
				mediaIDs.add(set.getInt(count));
				count++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mediaIDs;
	}
	
	public LinkedList<Integer> getAllMedia(String tag) {
		
		LinkedList<Integer> mediaIDs = new LinkedList<>();
		
		ResultSet set = Query.executeSelect("SELECT FK_FT_MediaID From TaggedMedia");
		int count = 1;
		try {
			while (set.next()) {
				
				mediaIDs.add(set.getInt(count));
				count++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mediaIDs;
		
	}
	
	public String getMediaTitle(int mediaID) {
		ResultSet set = Query.executeSelect("SELECT MediaTitle FROM Media WHERE MediaId = " + mediaID);
		try {
			set.next();
			return set.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Date getLastOpened(int mediaID) {
		ResultSet set = Query.executeSelect("SELECT MediaLastOpened FROM Media WHERE MediaId = " + mediaID);
		try {
			set.next();
			return set.getDate(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDateCreated(int mediaID) {
		ResultSet set = Query.executeSelect("SELECT MediaDateCreated FROM Media WHERE MediaId = " + mediaID);
		try {
			set.next();
			return set.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File getMediaFile(int mediaID) {
		ResultSet set = Query.executeSelect("SELECT MediaFileName FROM Media WHERE MediaID = " + mediaID);
		File dest;
		try {
			dest = new File(set.getString("MediaFileName"));
		} catch (SQLException e1) {
			return null;
		}
		
		try(Socket fileServerConnection = new Socket("localhost",1);
				DataOutputStream toServer = new DataOutputStream(fileServerConnection.getOutputStream());
				DataInputStream fromServer = new DataInputStream(fileServerConnection.getInputStream());
				FileOutputStream toFile = new FileOutputStream(dest);)
		{
		
			toServer.writeUTF("getfile~" + dest.getName());
			
			if (fromServer.readUTF().equals("OKAY")) {
				
				int read = 0;
				byte[] buff = new byte[1024];
				while ( ( read = fromServer.read(buff) ) != -1 ) {
					
					toFile.write(buff, 0, read);
					
				}
				
				return dest;
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getMediaViews(int mediaID) {
		//TODO stub
		return 0;
	}
	
	public String getDimensions(int mediaID) {
		//TODO stub
		return "100x100";
	}
	
	public String getRuntime(int mediaID) {
		//TODO stub
		return "00.00";
	}
	
	public String getAlbumTitle(int mediaID) {
		//TODO stub
		return "Album Title";
	}
	
}