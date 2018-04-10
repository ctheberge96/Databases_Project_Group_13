package com.group13.main;

import java.io.File;
import java.util.Scanner;

import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.queries.User;
import com.group13.scenes.AppScene;
import com.group13.scenes.CAdminScene;
import com.group13.scenes.CCreatorScene;
import com.group13.scenes.CLoginScene;
import com.group13.scenes.CMediaScene;
import com.group13.scenes.CUserScene;

public class MediaCenterApplication {

	private static AppScene currentScene;

	public static User currentUser;
	
	public static void main(String[] args) {
		
		User.registerNewUser(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		User admin = new User(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		Creator.registerCreator(admin, "0", "0");
		User.registerNewUser("creator", "creatorpass");
		User testCreator = new User("creator", "creatorpass");
		Creator.registerCreator(testCreator, "011400071", "12345678901234");
		
		Media.addMedia(new Creator(admin).getID(), "The Most Successful Pirate", Media.TYPE_VIDEO, new File("C:\\Users\\thebergec\\Desktop\\pirate.mp4"));
		Media.addMedia(new Creator(admin).getID(), "The Truth", Media.TYPE_IMAGE, new File("C:\\Users\\thebergec\\Desktop\\rosenberg.png"));
		
		//Something that runs when the program is shutdown
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
	      public void run()
	      {
	    	  INPUT_SCANNER.close();
	    	  Media.deleteMedia(new Media("The Most Successful Pirate"));
	    	  Media.deleteMedia(new Media("The Truth"));
	      }
	    });
		
		//Changes the scene to the initial scene
		changeScene(LOGIN_SCENE);
		
	}
	
	//All scenes which can be switched to
	public static final CMediaScene MEDIA_SCENE = new CMediaScene();
	public static final CAdminScene ADMIN_SCENE = new CAdminScene();
	public static final CCreatorScene CREATOR_SCENE = new CCreatorScene();
	public static final CLoginScene LOGIN_SCENE = new CLoginScene();
	public static final CUserScene USER_SCENE = new CUserScene();
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