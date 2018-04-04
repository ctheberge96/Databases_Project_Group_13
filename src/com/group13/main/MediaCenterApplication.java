package com.group13.main;

import com.group13.scenes.AdminScene;
import com.group13.scenes.AppScene;
import com.group13.scenes.CreatorScene;
import com.group13.scenes.ImageScene;
import com.group13.scenes.LoginScene;
import com.group13.scenes.MusicScene;
import com.group13.scenes.UserScene;
import com.group13.scenes.VideoScene;

public class MediaCenterApplication {

	public static void main(String[] args) {
		
		changeScene(LOGINSCENE);
		
	}
	
	public static final VideoScene VIDEOSCENE = new VideoScene();
	public static final ImageScene IMAGESCENE = new ImageScene();
	public static final MusicScene MUSICSCENE = new MusicScene();
	public static final AdminScene ADMINSCENE = new AdminScene();
	public static final CreatorScene CREATORSCENE = new CreatorScene();
	public static final LoginScene LOGINSCENE = new LoginScene();
	public static final UserScene USERSCENE = new UserScene();
	
	/**
	 * Sets the current scene to the given one and displays it.
	 */
	public static void changeScene(AppScene scene) {
		
		System.out.println(); //Put some distance between the last scene and this one
		scene.onStart();
		
	}
	
}