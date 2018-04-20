package com.group13.util;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A standalone server which allows for the transfer of media files
 * for the Media Center Application.
 * 
 * <br>Must be run alongside the Media Center Application.
 * 
 * @author Conner Theberge
 */
public class FileServer {

	private static final String COMMAND_ADDFILE = "addfile";
	private static final String COMMAND_GETFILE = "getfile";
	private static final String COMMAND_DELETEFILE = "deletefile";
	private static final String ERROR = "INCORRECT USAGE! CORRECT: <command>~<filename>";
	private static final String OKAY = "OKAY";
	
	private static DataOutputStream toClient;
	private static DataInputStream fromClient;
	private static Socket client;
	private static ServerSocket server;
	
	private static ArrayList<File> toDelete = new ArrayList<>();
	
	public static void main(String[] args) {
		
		ArrayList<Integer> deleteIndexes = new ArrayList<>();
		
		new File("./fileserver_media").mkdirs();
		
		try {
			server = new ServerSocket(1);
			System.out.println("Created server");
		} catch (IOException e) {
			System.exit(0);
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
	      public void run()
	      {
	    	  try {
				server.close();
				System.out.println("Closed server");
			} catch (IOException e) {}
	      }
	    });
		
		while(true) {
				
			for (int i = 0; i < toDelete.size(); i++) {
				
				if (!toDelete.get(i).exists() || toDelete.get(i).delete()) {
					
					deleteIndexes.add(i);
					
				}
				
			}
			
			for (int i = 0; i < deleteIndexes.size(); i++) {
				
				toDelete.remove(deleteIndexes.get(i).intValue());
				
			}
			
			deleteIndexes.clear();
			
			System.out.println("Waiting for client");
			
			try {
				
				client = server.accept();
				System.out.println("Accepted client");
				
				toClient = new DataOutputStream(client.getOutputStream());
				fromClient = new DataInputStream(client.getInputStream());
			
				handleInput(fromClient.readUTF());
				
			} catch (IOException e) {
				System.out.println("Error with client");
				closeAll();
				
			}
			
		}
		
	}
	
	/**
	 * Closes all client related objects.
	 */
	private static void closeAll() {
		
		try {
			toClient.close();
		} catch (IOException e1) {}
		try {
			fromClient.close();
		} catch (IOException e1) {}
		try {
			client.close();
		} catch (IOException e1) {}
		
	}
	
	/**
	 * Receives a file from the user, assuming
	 * that the user is already sending data.
	 * 
	 * @param filePath The path of the file to put the data into
	 */
	public static void recieveFile(String filePath) {
		
		System.out.println("Recieving file");

		File file = new File(filePath);
		
		try (FileOutputStream toFile = new FileOutputStream(file);) {
			
			byte[] byteBuff = new byte[1024];
			int read = 0;
			
			while ( (read = fromClient.read(byteBuff)) != -1 ) {
				
				toFile.write(byteBuff, 0, read);
				
			}
		
		} catch (IOException e) { System.out.println("Error while recieving"); }
		
		System.out.println("Recieved file");
		
	}
	
	/**
	 * Transfers a file to the user, assuming
	 * that the user is ready to receive data.
	 * 
	 * @param filePath The path of the file to take the data from
	 */
	public static void transferFile(String filePath) {
		
		System.out.println("Transfering file");
		
		File file = new File(filePath);
				
		try (FileInputStream fromFile = new FileInputStream(file)) {
			
			byte[] byteBuff = new byte[1024];
			int read = 0;
			int total = 0;
			
			while ( (read = fromFile.read(byteBuff)) != -1 ) {
				
				toClient.write(byteBuff, 0, read);
				total += read;
				System.out.printf("%.1f%%%n",((double)total / file.length()) * 100);
				
			}
		
		} catch (IOException e) { System.out.println("Error while transfering"); }

		try {
			client.close();
		} catch (IOException e) {}
		
		try {
			toClient.close();
		} catch (IOException e) {}
		
		try {
			fromClient.close();
		} catch (IOException e) {}
		
		System.out.println("Transfered file");
		
	}
	
	/**
	 * Deletes the file associated with the given path
	 * 
	 * @param filePath The path to the file to delete
	 */
	public static void deleteFile(String filePath) {
		
		File file = new File(filePath);
		
		if (!file.exists() || toDelete.contains(file)) {
			
			return;
			
		}
		
		toDelete.add(file);
		
	}
	
	/**
	 * Handles input from the user.
	 * 
	 * @param input The complete input received from the user
	 */
	public static void handleInput(String input) {
		
		String[] splits = input.split("~");
		
		if (splits.length == 1) {
			
			try {
				System.out.println("Input incorrect.");
				toClient.writeUTF(ERROR);
			} catch (IOException e) {}
			
		}
		
		System.out.println("Command: " + splits[0] + " on " + splits[1]);
		
		if (splits[0].equals(COMMAND_ADDFILE)) {
			
			try {
				toClient.writeUTF(OKAY);
			} catch (IOException e) {}
			recieveFile("./fileserver_media/" + splits[1]);
			
		} else if (splits[0].equals(COMMAND_GETFILE)) {
			
			try {
				toClient.writeUTF(OKAY);
			} catch (IOException e) {}
			transferFile("./fileserver_media/" + splits[1]);
			
		} else if (splits[0].equals(COMMAND_DELETEFILE)) {
			
			try {
				toClient.writeUTF(OKAY);
			} catch (IOException e) {}
			
			deleteFile("./fileserver_media/" + splits[1]);
			
		} else {
			
			try {
				System.out.println("UNKNOWN COMMAND");
				toClient.writeUTF(ERROR);
			} catch (IOException e) {}
			
		}
		
	}
	
}