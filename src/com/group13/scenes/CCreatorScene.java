package com.group13.scenes;

/*
 * NOTE!!!
 * 
 * This is a static import. It helps so you don't have to type
 * "MediaCenterApplication" every time you need something from that class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import java.io.File;
import java.util.LinkedList;

import com.group13.queries.Creator;
import com.group13.queries.Media;
import com.group13.scenes.AppScene;
import com.group13.util.Menu;

/**
 * 
 * TODO Comment this!
 * 
 * @author Jason Moy
 */
public class CCreatorScene extends AppScene {
	
	private Menu menu = new Menu("Creator Menu");
	private Menu mediaMenu = new Menu("Created Media");
	private Menu curMenu;
	private Creator creator;
	
	public CCreatorScene() {
	
		menu.addOption("View Created Media", () -> {
		
			mediaMenu.clearOptions();
			
			LinkedList<Media> allMedia = creator.getCreatedMedia();
			
			for (Media media : allMedia) {
				
				//This is a sub menu for each media the creator created.
				//These are different menus, for one specific media each. Keep that in mind
				mediaMenu.addOption(media.getMediaTitle(), () -> {
					
					Menu singleMediaMenu = new Menu(media.getMediaTitle());
					
					//In this sub menu, if you need the media title, just use media.getMediaTitle()
					
					singleMediaMenu.addOption("Play", () -> {
						
						//TODO
						//What happens when they press play?
						
					});
					
					singleMediaMenu.addOption("Delete", () -> {
						
						//TODO
						//What happens when they press delete?
						
					});
					
					singleMediaMenu.addOption("Back", () -> {
						
						//TODO
						//What happens when they press back? Where do they go?
						
					});
					
					//When they click on this option for this specific video,
					//it changes menu to the sub menu just created
					curMenu = singleMediaMenu;
					
				});
				
			}
			
			mediaMenu.addOption("Back", () -> {
				
				//TODO
				//What happens when they go back? Where do they go?
				
			});
			
			curMenu = mediaMenu;
			
		});
		
		menu.addOption("Add New Media", () -> {
			
			//TODO
			//What happens when the creator wants to add media?
			//Surely you need a path to the file they want to add.
			//Does the file exist?
			//Make sure you actually add the file to the database.
			//Keep the user informed!
			
		});
		
		menu.addOption("View Finances", () -> {
			
			//TODO
			//What finances do they need to see?
			//Admin has a static variable for how much $$ is received per view.
			
		});
		
		menu.addOption("Back", () -> {
			
			//TODO
			//Where do they go when they want to go back?
			
		});
		
	}
	
	private boolean validateFile(File file) {
		
		//TODO
		//Does the file exist?
		//Is it a file we support? (Image, Video, or Music) (look at the extension!)
		return false;
		
	}
	
	private char getFileType(File file) {
		
		//TODO
		//What type of file is it?
		return Media.TYPE_UNKNOWN;
		
	}

	@Override
	public void onStart() {
		
		//TODO
		//What happens when this scene starts up?
		
	}

	@Override
	public void onExit() {
		
		//TODO
		//What happens when this scene exits?
		
	}

}