package com.group13.scenes;

/*
 * NOTE!!!
 * 
 * This is a static import. It helps so you don't have to type
 * "MediaCenterApplication" every time you need something from that class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.scenes.AppScene;
import com.group13.util.Menu;

/**
 * 
 * TODO Comment this!
 * 
 * @author Jason Moy
 */
public class CUserScene extends AppScene {
	
	private void initMainMenu() {
		
		mainMenu.clearOptions();
		
		mainMenu.addOption("Search Media", () -> {
			
			//TODO
			//How would you search through all media?
			
		});
		
		mainMenu.addOption("View Media (All)", () -> {
				
			//TODO
			//How would you view all media?
					
		});
		
		/*mainMenu.addOption("View Media (Favorites)", () -> {
			
			//Unimplemented due to time constraints.
			
		});*/
		
		/*mainMenu.addOption("View Media (Followed Creators)", () -> {
			
			//Unimplemented due to time constraints.
			
		});*/
		
		if (Creator.isCreator(currentUser)) {
			
			//TODO
			//This user is a creator. Should they get any special options because of this?
			
		} else {
			
			//TODO
			//This user is not a creator. Maybe make sure the special options added for a creator
			//truly aren't there anymore?
			
		}
		
		if (Admin.isAdmin(MediaCenterApplication.currentUser)) {
			
			//TODO
			//This user is the admin. Should they get any special options because of this?
			
		} else {
			
			//TODO
			//This user is not the admin. Maybe make sure the special options added for the admin
			//truly aren't there anymore?
			
		}
		
		mainMenu.addOption("Account Settings", () -> {
			
			//TODO
			//What happens when they click this option?
			
		});
		
		mainMenu.addOption("Exit Application", () -> {
			
			System.exit(0);
			
		});
		
	}
	
	private Menu mainMenu = new Menu("Media Center");
	
	private Menu accountMenu = new Menu("Account Settings");

	public CUserScene() {
		
		accountMenu.addOption("Change Username", () -> {
			
			//TODO
			//How do we change username?
			//Remember, username must be unique!
			
		});
		
		accountMenu.addOption("Change Password", () -> {
			
			//TODO
			//How do we change password?
			//Unlike username, password does not need to be unique.
					
		});
		
		accountMenu.addOption("Request Creator Status", () -> {
			
			//TODO
			//What happens when we select this option?
			
		});
		
		accountMenu.addOption("Delete Account", () -> {
			
			//TODO
			//Show should we delete the account?
			
		});
		
		accountMenu.addOption("Log Out", () -> {
			
			//TODO
			//What happens when they log out?
			
		});
		
		accountMenu.addOption("Back", () -> {
			
			//TODO
			//Where do they go back?
			
		});
		
	}
	
	@Override
	public void onStart() {
		
		//TODO
		//What happens when this scene starts?
		
	}

	@Override
	public void onExit() {

		//TODO
		//What happens when they exit this scene? 
		
	}

}