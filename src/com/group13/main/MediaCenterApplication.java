package com.group13.main;

/* 
 * Conner Theberge
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */ 

import java.io.File;
import java.util.Scanner;

import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.queries.User;
import com.group13.scenes.AdminScene;
import com.group13.scenes.AppScene;
import com.group13.scenes.CreatorScene;
import com.group13.scenes.LoginScene;
import com.group13.scenes.MediaScene;
import com.group13.scenes.UserScene;

public class MediaCenterApplication {

	private static AppScene currentScene;

	public static User currentUser;
	
	public static void main(String[] args) {
		
		//Registers the admin
		User.registerNewUser(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		User admin = new User(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		Creator.registerCreator(admin, "0", "0");
		User.registerNewUser("creator", "creatorpass");
		User testCreator = new User("creator", "creatorpass");
		Creator.registerCreator(testCreator, "011400071", "12345678901234");
		
		//Adds test media
		Media.addMedia(new Creator(admin).getID(), "The Most Successful Pirate", Media.TYPE_VIDEO, new File("./data/pirate.mp4"));
		Media.addMedia(new Creator(admin).getID(), "The Truth", Media.TYPE_IMAGE, new File("./data/rosenberg.png"));
		Media.addMedia(new Creator(admin).getID(), "Desert Song", Media.TYPE_MUSIC, new File("./data/desert.mp3"));
		
		//Something that runs when the program is shutdown
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
	      public void run()
	      {
	    	  INPUT_SCANNER.close();
	      }
	    });
		
		currentUser = new User(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		
		//Changes the scene to the initial scene
		changeScene(LOGIN_SCENE);
		
	}
	
	//All scenes which can be switched to
	public static final MediaScene MEDIA_SCENE = new MediaScene();
	public static final AdminScene ADMIN_SCENE = new AdminScene();
	public static final CreatorScene CREATOR_SCENE = new CreatorScene();
	public static final LoginScene LOGIN_SCENE = new LoginScene();
	public static final UserScene USER_SCENE = new UserScene();
	public static final Scanner INPUT_SCANNER = new Scanner(System.in);
	
	/**
	 * Sets the current scene to the given one and displays it.
	 */
	public static void changeScene(AppScene scene) {
		
		if (currentScene != null) {
			
			currentScene.onExit();
			
		}
		
		System.out.println(); //Put some distance between the last scene and this one
		scene.onStart();
		
		currentScene = scene;
		
	}
	
}