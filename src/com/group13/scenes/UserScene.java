package com.group13.scenes;

import com.group13.main.MediaCenterApplication;
import com.group13.util.Menu;

public class UserScene extends AppScene {

	/* Search Media By Tag
	 * Search Media By Name
	 * View All Media
	 * Follow Tag
	 * View Favorites
	 * Request Upgrade
	 * 
	 * (Add a menu that contains all media, and if they select a media,
	 * load that media up in the MediaScene)
	 */
	
	private Menu mainMenu = new Menu("Media Center");
	
	private Menu accountMenu = new Menu("Account Settings");
	
	private Menu curMenu = mainMenu;
	private void changeMenu(Menu menu) {
		curMenu = menu;
	}
	
	public UserScene() {
		
		mainMenu.addOption("Search Media", () -> {
			
			
			
		});
		
		mainMenu.addOption("View Media (All)", () -> {
					
					
					
		});
		
		mainMenu.addOption("View Media (Favorites)", () -> {
			
			
			
		});
		
		mainMenu.addOption("View Media (Followed Creators)", () -> {
			
			
			
		});
		
		mainMenu.addOption("Account Settings", () -> {
			
			changeMenu(accountMenu);
			
		});
		
		mainMenu.addOption("Exit Application", () -> {
			
			System.exit(0);
			
		});
		
		accountMenu.addOption("Change Username", () -> {
			
			
			
		});
		
		accountMenu.addOption("Change Password", () -> {
					
					
					
		});
		
		accountMenu.addOption("Request Creator Status", () -> {
			
			
			
		});
		
		accountMenu.addOption("Delete Account", () -> {
			
			
			
		});
		
		accountMenu.addOption("Back", () -> {
			
			changeMenu(mainMenu);
			
		});
		
		//load media
		//MediaCenterApplication.changeScene(MediaCenterApplication.MEDIA_SCENE);
		
	}
	
	private boolean loop = true;
	
	@Override
	public void onStart() {
		
		MediaCenterApplication.changeScene(MediaCenterApplication.MEDIA_SCENE);
		
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