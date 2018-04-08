package com.group13.main;

import java.io.File;
import java.util.Scanner;

import com.group13.queries.Admin;
import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.queries.Query;
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
		
		User.registerNewUser(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		User admin = new User(Admin.ADMIN_USERNAME, Admin.ADMIN_PASSWORD);
		Creator.registerCreator(admin, "0", "0");
		
		Media.addMedia(new Creator(admin).getID(), "The Most Successful Pirate", Media.TYPE_VIDEO, new File("pirate.mp4"));
		
		//Something that runs when the program is shutdown
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
	      public void run()
	      {
	    	  INPUT_SCANNER.close();
	    	  Media.deleteMedia(new Media("The Most Successful Pirate"));
	      }
	    });
		
		//Changes the scene to the initial scene
		changeScene(MEDIA_SCENE);
		
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