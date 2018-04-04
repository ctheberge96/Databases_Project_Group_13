package com.group13.main;

import java.util.Scanner;

import com.group13.scenes.AdminScene;
import com.group13.scenes.AppScene;
import com.group13.scenes.CreatorScene;
import com.group13.scenes.ImageScene;
import com.group13.scenes.LoginScene;
import com.group13.scenes.MusicScene;
import com.group13.scenes.UserScene;
import com.group13.scenes.VideoScene;

public class MediaCenterApplication {

	private static AppScene currentScene;
	
	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
	      public void run()
	      {
	    	  INPUT_SCANNER.close();
	      }
	    });
		
		changeScene(USERSCENE);
		
	}
	
	public static final VideoScene VIDEOSCENE = new VideoScene();
	public static final ImageScene IMAGESCENE = new ImageScene();
	public static final MusicScene MUSICSCENE = new MusicScene();
	public static final AdminScene ADMINSCENE = new AdminScene();
	public static final CreatorScene CREATORSCENE = new CreatorScene();
	public static final LoginScene LOGINSCENE = new LoginScene();
	public static final UserScene USERSCENE = new UserScene();
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