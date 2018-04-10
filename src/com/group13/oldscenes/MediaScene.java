package com.group13.oldscenes;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.Media;
import com.group13.scenes.AppScene;
import com.group13.util.Menu;

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
	private Menu mediaMenu = new Menu ("Media Menu");
	
	private int mediaID;
	public void loadMedia(int mediaID) {
		
		this.mediaID = mediaID;
		
	}
	
	public MediaScene() {
		//mediaMenu.print();
		//mediaMenu.getInput(MediaCenterApplication.INPUT_SCANNER);
		
	
	}
	
	
	
	@Override
	public void onStart() {
		
		Media MediaI = new Media("The Most Successful Pirate");
		
		System.out.print("Title: " + MediaI.getMediaFileName());
		System.out.print("Type: " + MediaI.getMediaType());
		System.out.print("Date created: " + MediaI.getDateCreated());
		System.out.print("Total views" + MediaI.getMediaViews());
		
		mediaMenu.addOption("Play", () -> {
			try {
				Desktop.getDesktop().open(new File("pirate.mp4"));
				mediaMenu.print();
				mediaMenu.getInput(MediaCenterApplication.INPUT_SCANNER);
			
			} 
			catch (IOException e) {
				
				
			}
		
		} );
		
		mediaMenu.addOption("Back", () -> {
			MediaCenterApplication.changeScene(MediaCenterApplication.USER_SCENE);
			
		} );
		
		
		//print out all the information
		//start a menu asking to open, or go back
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
		
		
	}

}

