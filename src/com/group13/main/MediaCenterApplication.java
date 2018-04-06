package com.group13.main;

import java.util.Scanner;

import com.group13.scenes.AdminScene;
import com.group13.scenes.AppScene;
import com.group13.scenes.CreatorScene;
import com.group13.scenes.LoginScene;
import com.group13.scenes.MediaScene;
import com.group13.scenes.UserScene;

public class MediaCenterApplication {

	private static AppScene currentScene;
	
	public enum Rank { USER, CREATOR, ADMIN }
	public static Rank USER_RANK;
	
	public static void main(String[] args) {
		
		//Something that runs when the program is shutdown
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
	      public void run()
	      {
	    	  INPUT_SCANNER.close();
	      }
	    });
		
		//Changes the scene to the initial scene
		changeScene(USER_SCENE);
		
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