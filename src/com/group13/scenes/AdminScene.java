package com.group13.scenes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.Media;
import com.group13.util.Menu;

/*
 * Admin Scene
 * @author Tyler Crosby
 * @author Connor Theberge
 * 
 * 
 */

public class AdminScene extends AppScene {
	
	private Menu accountMenu = new Menu("Account Settings");
	
	private Menu adminMenu = new Menu("Admin Menu");
	
	private Menu curMenu = adminMenu;
	private void changeMenu(Menu menu) {
		curMenu = menu;
	}
	
	public AdminScene() {
	
		accountMenu.addOption("Change Username", () -> {
			
			//TAKE FROM USERSCENE
			
		});
		
		accountMenu.addOption("Change Password", () -> {
					
			//TAKE FROM USERSCENE	
					
		});
	
		accountMenu.addOption("Back", () -> {
			
			changeMenu(adminMenu);
			
		});
	//Admin Menu
		adminMenu.addOption("Add Media", () -> {
			
			String mediaTitle;
			char mediaType;
			String mediaPath;
			int result;
						
			System.out.print("Enter a Media Title: ");
			mediaTitle = MediaCenterApplication.INPUT_SCANNER.nextLine();
			System.out.println();
			System.out.print("Select a media type: [v]ideo, [m]usic, [i]mage, or [?]");
			mediaType = MediaCenterApplication.INPUT_SCANNER.nextLine().charAt(0);
			System.out.println();
			System.out.print("Enter file path: ");
			mediaPath = MediaCenterApplication.INPUT_SCANNER.nextLine();
			
			File mediaFile = new File(mediaPath);
			Media.addMedia(MediaCenterApplication.currentUser.getID(), mediaTitle, mediaType, mediaFile);
			
		});
		
		adminMenu.addOption("Delete Media", () -> {
			int mediaID;
			
			System.out.print("Enter the ID of the Media you wish to delete: ");
			mediaID = MediaCenterApplication.INPUT_SCANNER.nextInt();
			Media media = new Media(mediaID);
			Media.deleteMedia(media);
			
		});
		
		adminMenu.addOption("Account Settings", () -> {
			
			changeMenu(accountMenu);
			
		});
		
		adminMenu.addOption("Back", ()-> {
			
			MediaCenterApplication.changeScene(MediaCenterApplication.USER_SCENE);
			
		});
		
		adminMenu.addOption("Change a User's account info", () -> {
			/*
			 * Asks what user -> lets say John Doe.
			 * 
			 * Returns John's account info, IF ARTIST -> financial information, statistics, account payable info.
			 * 
			 * Asks what admin wants to change (switch statement)
			 */
			System.out.println();
			
			
		});
		
		
	}

	private boolean loop = true;
	
	@Override
	public void onStart() {
		while (loop) {
			
			curMenu.print();
			curMenu.getInput(MediaCenterApplication.INPUT_SCANNER);
			System.out.println();
			
		}
	}

	@Override
	public void onExit() {
		
		loop = false;
		
	}

}