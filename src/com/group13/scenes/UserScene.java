package com.group13.scenes;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */

/*
 * NOTE!!!
 * 
 * This is a static import. It helps so you don't have to type
 * "MediaCenterApplication" every time you need something from that class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import java.util.LinkedList;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.queries.User;
import com.group13.scenes.AppScene;
import com.group13.util.Menu;

/**
 * UserScene contains options for viewing media, accessing creator/admin scenes,
 * and changing account information.
 * 
 * @author Conner Theberge
 */
public class UserScene extends AppScene {
	
	private Menu curMenu;
	
	private void initMainMenu() {
		
		mainMenu.clearOptions();
		
		mainMenu.addOption("Search Media", () -> {
			
			Menu searchResults = new Menu("Search Results");
			
			System.out.println("Enter in a search term:");
			String searchTerm = INPUT_SCANNER.nextLine().toLowerCase();
			
			LinkedList<Media> allMedia = Media.getAllMedia();
			
			for (Media media : allMedia) {
				
				if (media.getMediaTitle().toLowerCase().contains(searchTerm)) {
					
					searchResults.addOption(media.getMediaTitle(), () -> {
						
						System.out.println("\nLoading \"" + media.getMediaTitle() + "\"");
						
						MEDIA_SCENE.loadMedia(media);
						changeScene(MEDIA_SCENE);
						
					});
					
				}
				
			}
			
			searchResults.addOption("Back", () -> {
				curMenu = mainMenu;
			});
			
			curMenu = searchResults;
			
		});
		
		mainMenu.addOption("View Media (All)", () -> {
				
			Menu mediaMenu = new Menu("All Media");
			
			LinkedList<Media> allMedia = Media.getAllMedia();
			
			for (Media media : allMedia) {
					
				mediaMenu.addOption(media.getMediaTitle(), () -> {
					
					System.out.println("\nLoading \"" + media.getMediaTitle() + "\"");
					
					MEDIA_SCENE.loadMedia(media);
					changeScene(MEDIA_SCENE);
					
				});
				
			}
			
			mediaMenu.addOption("Back", () -> {
				curMenu = mainMenu;
			});
			
			curMenu = mediaMenu;
					
		});
		
		/*mainMenu.addOption("View Media (Favorites)", () -> {
			
			//Unimplemented due to time constraints.
			
		});*/
		
		/*mainMenu.addOption("View Media (Followed Creators)", () -> {
			
			//Unimplemented due to time constraints.
			
		});*/
		
		if (Creator.isCreator(currentUser)) {
			
			mainMenu.addOption("Creator Menu", () -> {
				changeScene(CREATOR_SCENE);
			});
			
		}
		
		if (Admin.isAdmin(MediaCenterApplication.currentUser)) {
			
			mainMenu.addOption("Admin Menu", () -> {
				changeScene(ADMIN_SCENE);
			});
			
		}
		
		mainMenu.addOption("Account Settings", () -> {
			
			curMenu = accountMenu;
			
		});
		
	}
	
	private Menu mainMenu = new Menu("Media Center");
	
	private Menu accountMenu = new Menu("Account Settings");

	public UserScene() {
		
		accountMenu.addOption("Change Username", () -> {
			
			String newUsername;
			
			while(true) {
				System.out.println("Enter in a new username:");
				newUsername = INPUT_SCANNER.nextLine();
				
				if (User.usernameExists(newUsername)) {
					System.out.println("Username exists. Choose another.\n");
				} else {
					break;
				}
				
			}
			
			if (currentUser.changeUsername(newUsername)) {
			
				System.out.println("Username changed.");
			
			} else {
				
				System.out.println("Error changing username. Try again later.");
				
			}
			
		});
		
		accountMenu.addOption("Change Password", () -> {
			
			String newPassword;
			
			System.out.println("Enter in a new password:");
			newPassword = INPUT_SCANNER.nextLine();
			
			if (currentUser.changePassword(newPassword)) {
			
				System.out.println("Password changed.");
			
			} else {
				
				System.out.println("Error changing password. Try again later.");
				
			}
					
		});
		
		accountMenu.addOption("Request Creator Status", () -> {
			
			if (currentUser.getCreatorStatus() != User.CREATOR_STATUS_ACCEPTED) {
				
				currentUser.requestCreatorStatus();
				
			}
			
		});
		
		accountMenu.addOption("Delete Account", () -> {
			
			System.out.println("\nAre you sure you want to delete your account, " + currentUser.getUsername() + "?");
			String in = "";
			while(!in.equalsIgnoreCase("y") && !in.equalsIgnoreCase("n")) {
				System.out.print("(y/n): ");
				in = INPUT_SCANNER.nextLine();
			}
			if (in.equalsIgnoreCase("y")) {
				User.deleteUser(currentUser);
				System.out.println("\nAccount deleted.\n");
				changeScene(LOGIN_SCENE);
			}
			
		});
		
		accountMenu.addOption("Log Out", () -> {
			
			System.out.println("\nLogged out.\n");
			changeScene(LOGIN_SCENE);
			
		});
		
		accountMenu.addOption("Back", () -> {
			
			curMenu = mainMenu;
			
		});
		
		curMenu = mainMenu;
		
	}
	
	@Override
	public void onStart() {
		
		initMainMenu();
		
		curMenu = mainMenu;
		
		while(true) {
			curMenu.print();
			curMenu.getInput(INPUT_SCANNER);
		}
		
	}

	@Override
	public void onExit() {}

}