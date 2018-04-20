package com.group13.scenes;

/* 
 * Jason Moy
 * Group 13
 * Comp 2650, Databases 
 * Media Center Application
 */ 

/*
 * NOTE!!!
 * 
 * This is a static import. It helps so you don't have to type
 * "MediaCenterApplication" every time you need something from the class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.group13.main.MediaCenterApplication;
import com.group13.queries.Media;
import com.group13.scenes.AppScene;
import com.group13.util.Menu;

/**
 * 
 * TODO Comment this!
 * 
 * @author Jason Moy
 */
public class MediaScene extends AppScene {
	
	private Menu mediaMenu = new Menu ("Media Menu");
	
	private Media loadedMedia;
	private File loadedMediaFile;
	
	public void loadMedia(Media media) {
		
		loadedMedia = media;
		loadedMediaFile = loadedMedia.getMediaFile();
		
	}
	
	public MediaScene() {
		
		mediaMenu.addOption("Play", () -> {
			try {
				Desktop.getDesktop().open(loadedMediaFile);
				loadedMedia.addView();
				mediaMenu.print();
				mediaMenu.getInput(MediaCenterApplication.INPUT_SCANNER);
			} 
			catch (IOException e) {
				System.out.print("Media cannot be played");
				MediaCenterApplication.changeScene(MediaCenterApplication.MEDIA_SCENE);
			}
			//What happens when they press play?
			//Don't forget to add a view to the media!
			
		} );
		
		mediaMenu.addOption("Back", () -> {
			MediaCenterApplication.changeScene(MediaCenterApplication.USER_SCENE);
			//TODO
			//Where do they go from the media scene when they want to go back?
			
		} );
	
	}
	
	@Override
	public void onStart() {
		
		System.out.println("Title: " + loadedMedia.getMediaTitle());
		System.out.println("Type: " + loadedMedia.getMediaType());
		System.out.println("Date created: " + loadedMedia.getDateCreated());
		System.out.println("Total views: " + loadedMedia.getMediaViews());
		
		/*
		 * TODO
		 * 
		 * What happens when this scene starts?
		 * Did they load the media in? What if loadedMediaFile is null?
		 * Obviously you want the menu to appear and the menu to get input.
		 * Do you have to loop the menu?
		 * What information do you have to display to the user?
		 * type "loadedMedia." and let that box show up to tell you all the functions.
		 * Look at what functions contain information that the user should know.
		 */
		
		mediaMenu.print();
		mediaMenu.getInput(INPUT_SCANNER);
		
	}

	@Override
	public void onExit() {
		
		/*
		 * TODO
		 * 
		 * Should something happen when the scene is exited?
		 * Should we delete the loadedMediaFile?
		 */
		
	}

}

