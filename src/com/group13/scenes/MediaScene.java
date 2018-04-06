package com.group13.scenes;

import java.awt.Desktop;
import java.io.File;

/**
 * This scene is for any playable media.
 * <br>This scene has the ability to load media before it is shown.
 * <br>This scene shows the user information about the media before
 * being asked if they would like to play the media
 * 
 * @author Conner Theberge
 */
public class MediaScene extends AppScene {
	
	/*
	 * Display media information, then
	 * have menu where they either select open media, or select "go back"
	 * 
	 * Information to display:
	 * Title
	 * Type (Image, Video, Music)
	 * Date Created
	 * Views
	 */
	
	private int mediaID;
	public void loadMedia(int mediaID) {
		
		this.mediaID = mediaID;
		
	}
	
	public MediaScene() {
		
	}

	@Override
	public void onStart() {
		
		//print out all the information
		//start a menu asking to open, or go back
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
		
		
	}

}