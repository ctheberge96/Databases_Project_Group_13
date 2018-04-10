package com.group13.scenes;

/*
 * NOTE!!!
 * 
 * This is a static import. It helps so you don't have to type
 * "MediaCenterApplication" every time you need something from the class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import com.group13.scenes.AppScene;
import com.group13.util.Menu;

/**
 * 
 * TODO Comment this!
 * 
 * @author Tyler Crosby
 */
public class CLoginScene extends AppScene {

	private Menu menu = new Menu("Login");
	
	public CLoginScene() {
		
		menu.addOption("Login to existing account", () -> {
	
			//TODO
			//Get the login information.
			//Does the username not exist? How should we inform the user?
			//Should there be a cancel option?
			//What happens if the user entered the correct login?
			
		});
		
		menu.addOption("Register new account", () -> {
			
			//TODO
			//Get the login information.
			//Does the username not exist? How should we inform the user?
			//Should there be a cancel option?
			//What happens if the user entered an already existing login?
			//NOTE: passwords don't have to be different, usernames do.
			
		});
		
		menu.addOption("Exit application", () -> {
			
			//TODO
			//How should the application be exited?
			//Should we tell the user?
			
		});
		
	}
	
	@Override
	public void onStart() {
		
		//TODO
		//What should happen when this scene is started?
		
	}

	@Override
	public void onExit() {
		
		//TODO
		//What happens when we exit the login scene?
		
	}

}