package com.group13.scenes;

/*
 * NOTE!!!
 * 
 * This is a static import. It helps so you don't have to type
 * "MediaCenterApplication" every time you need something from that class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import java.awt.Desktop;
import java.io.IOException;
import java.util.LinkedList;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.scenes.AppScene;
import com.group13.util.Menu;

/**
 * 
 * TODO Comment this!
 * 
 * @author Tyler Crosby
 */
public class CAdminScene extends AppScene {

	/*
	 * NOTE!!!
	 * 
	 * I never implemented this. Sorta on your own!
	 * Look at my base code and see if there's anything you want to keep
	 * or throw out.
	 * Lots of menus here. Ew.
	 */
	
	private Menu menu = new Menu("Admin Menu");
	private Menu accountMenu = new Menu("All Accounts");
	private Menu mediaMenu = new Menu("All Media");
	private Menu creatorMenu;
	private Menu creatorMediaMenu;
	private Menu singleMediaMenu;
	private Menu userMenu;
	private Menu curMenu;
	private Admin admin;
	
	public CAdminScene() {
	
		menu.addOption("View All Accounts", () -> {
			
			//admin.
			
		});
		
		menu.addOption("View All Media", () -> {
			
			
			
		});
		
		menu.addOption("View Creator Requests", () -> {
			
			
			
		});
		
		menu.addOption("Back", () -> {
			
			changeScene(USER_SCENE);
			
		});
		
	}

	@Override
	public void onStart() {
		
		curMenu = menu;
		
		while(true) {
			
			curMenu.print();
			curMenu.getInput(MediaCenterApplication.INPUT_SCANNER);
			
		}
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

}