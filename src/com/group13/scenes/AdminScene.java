package com.group13.scenes;

import java.util.Scanner;

import com.group13.main.MediaCenterApplication;
import com.group13.util.Menu;

/*
 * Admin Scene
 * @author Tyler Crosby
 * @author Connor Theberge
 * 
 * 
 */

public class AdminScene extends AppScene {
	
	private Menu mainMenu = new Menu("Media Center");
	
	private Menu accountMenu = new Menu("Account Settings");
	
	private Menu adminMenu = new Menu("Admin Menu");
	
	private Menu curMenu = mainMenu;
	private void changeMenu(Menu menu) {
		curMenu = menu;
	}
	
	public AdminScene() {

		mainMenu.addOption("Search Media", () -> {
			
			//TAKE FROM USERSCENE
			
		});
		
		mainMenu.addOption("View Media (All)", () -> {
					
			//TAKE FROM USERSCENE	
					
		});
		
		mainMenu.addOption("View Media (Favorites)", () -> {
			
			//TAKE FROM USERSCENE
			
		});
		
		mainMenu.addOption("View Media (Followed Creators)", () -> {
			
			//TAKE FROM USERSCENE
			
		});
	
		mainMenu.addOption("Administrator Options", () -> {
			/*
			 * CHANGE MENU TO ADMIN SPECIFIC MENU
			 * @author Tyler Crosby
			 */
			changeMenu(adminMenu);
			
		});
		
		mainMenu.addOption("Account Settings", () -> {
			
			changeMenu(accountMenu);
			
		});
		
		mainMenu.addOption("Exit Application", () -> {
			
			System.exit(0);
			
		});
	
		accountMenu.addOption("Change Username", () -> {
			
			//TAKE FROM USERSCENE
			
		});
		
		accountMenu.addOption("Change Password", () -> {
					
			//TAKE FROM USERSCENE	
					
		});
	
		accountMenu.addOption("Back", () -> {
			
			changeMenu(mainMenu);
			
		});
	//Admin Menu
		adminMenu.addOption("Add Media", () -> {
			
			
			
		});
		
		adminMenu.addOption("Delete Media", () -> {
			
			
			
		});
		
		adminMenu.addOption("Modify Media", () -> {
			
			
			
		});
		adminMenu.addOption("Back", ()-> {
			
			changeMenu(mainMenu);
			
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