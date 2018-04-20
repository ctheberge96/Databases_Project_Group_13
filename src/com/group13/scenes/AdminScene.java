package com.group13.scenes;

import java.io.File;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.queries.User;
import com.group13.util.Menu;

/* 
 * Tyler Crosby
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */ 

public class AdminScene extends AppScene {
	
	private Menu accountMenu = new Menu("Account Settings");
	
	private Menu adminMenu = new Menu("Admin Menu");
	
	private Menu curMenu = adminMenu;
	private void changeMenu(Menu menu) {
		curMenu = menu;
	}
	
	public AdminScene() {	
	//Admin Menu
		adminMenu.addOption("Add Media", () -> {
			
			String mediaTitle;
			char mediaType;
			String mediaPath;

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
			String mediaTitle;
			
			System.out.print("Enter the title of the Media you wish to delete: ");
			mediaTitle = MediaCenterApplication.INPUT_SCANNER.nextLine();
			Media media = new Media(mediaTitle);
			Media.deleteMedia(media);
			
		});
		
		adminMenu.addOption("Account Settings", () -> {
			
			changeMenu(accountMenu);
			
		});
		
		adminMenu.addOption("Back", ()-> {
			
			MediaCenterApplication.changeScene(MediaCenterApplication.USER_SCENE);
			
		});
		
		adminMenu.addOption("View a Creator's account info", () -> {
			int lookUp;
			System.out.print("Enter the ID of the User you wish to look up: ");
			lookUp = MediaCenterApplication.INPUT_SCANNER.nextInt();
			MediaCenterApplication.INPUT_SCANNER.nextLine();
			User user = new User(lookUp);
			Creator creator = new Creator(user);
			if(Creator.isCreator(user)) {
				System.out.println("CREATOR FOUND:");
				System.out.print("Creator username: ");
				System.out.println(user.getUsername());
				System.out.print("Creator password: ");
				System.out.println(user.getPassword());
				System.out.print("Creator total plays: ");
				System.out.println(creator.getTotalPlays());
				System.out.print("Creator Bank Acc #: ");
				System.out.println(creator.getBankAccountNumber());
				System.out.print("Creator Routing #: ");
				System.out.println(creator.getBankRouting());
				
				char choice;
				boolean sChoice = true;
				while(sChoice) {
					System.out.println("Would you like to change anything? [u]sername, [p]assword");
					choice = MediaCenterApplication.INPUT_SCANNER.nextLine().charAt(0);
					
					switch (choice) {
					case 'u':
						String newUsername;
						System.out.println("Changing Creator username");
						System.out.print("Enter change: ");
						newUsername = MediaCenterApplication.INPUT_SCANNER.nextLine();
						user.changeUsername(newUsername);
						sChoice = false;
						break;
					case 'p':
						String newPassword;
						System.out.println("Changing Creator password");
						System.out.print("Enter change: ");
						newPassword = MediaCenterApplication.INPUT_SCANNER.nextLine();
						user.changePassword(newPassword);
						sChoice = false;
						break;
					default:
						System.out.println("Non-valid choice ");
					}
				}
			}
			else {
				System.out.println("That user is not a creator.");
			}
			
			
		});
	
	//Account Menu
		accountMenu.addOption("Change Username", () -> {
			
			System.out.print("Enter a new username: ");
			String newUsername = MediaCenterApplication.INPUT_SCANNER.nextLine();
			MediaCenterApplication.currentUser.changeUsername(newUsername);
			
			System.out.println();
			System.out.println("New username accepted: "+newUsername+ ". Don't lose it!");
			
		});
		
		accountMenu.addOption("Change Password", () -> {
					
			System.out.print("Enter a new password: ");
			String newPassword = MediaCenterApplication.INPUT_SCANNER.nextLine();
			MediaCenterApplication.currentUser.changePassword(newPassword);
			
			System.out.println();
			System.out.println("New username accepted: "+newPassword+ ". Don't lose it!");
					
		});
	
		accountMenu.addOption("Back", () -> {
			
			changeMenu(adminMenu);
			
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