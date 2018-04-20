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
 * "MediaCenterApplication" every time you need something from that class.
 * Just type what you need (such as changeScene, or INPUT_SCANNER)
 */
import static com.group13.main.MediaCenterApplication.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.group13.main.MediaCenterApplication;
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
public class CreatorScene extends AppScene {
	
	private Menu menu = new Menu("Creator Menu");
	private Menu mediaMenu = new Menu("Created Media");
	private Menu curMenu;
	private Creator creator;
	private String addMedia;
	private String pathFile;
	
	public CreatorScene() {
	
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
						try {
							Desktop.getDesktop().open(media.getMediaFile());
						} catch (IOException e) {
							System.out.print("There is nothing here.");
							
						}
						//TODO
						//What happens when they press play?
						
					});
					
					singleMediaMenu.addOption("Delete", () -> {
						Media.deleteMedia(media);
						System.out.println("The media has been deleted.");
						curMenu = menu;
						
						//TODO
						//What happens when they press delete?
						
					});
					
					singleMediaMenu.addOption("Back", () -> {
						curMenu=menu;
						//TODO
						//What happens when they press back? Where do they go?
						
					});
					
					//When they click on this option for this specific video,
					//it changes menu to the sub menu just created
					curMenu = singleMediaMenu;
					
				});
				
			}
			
			mediaMenu.addOption("Back", () -> {
				curMenu=menu;

				//TODO
				//What happens when they go back? Where do they go?
				
			});
			
			curMenu = mediaMenu;
			
		});
		
		menu.addOption("Add New Media", () -> {
			System.out.print("What is the title of the media?");
			addMedia = INPUT_SCANNER.nextLine();
			System.out.print("What is the path of the file?");
			pathFile = INPUT_SCANNER.nextLine();
			File file = new File(pathFile);
			
			if (validateFile(file)==true){
				Media.addMedia(creator.getID(), addMedia, getFileType(file), file);
				System.out.print("The file has been added.");
			}
			else{
				System.out.print("Please enter a valide file type.");
			}
			
			
			//TODO
			//What happens when the creator wants to add media?
			//Surely you need a path to the file they want to add.
			//Does the file exist?
			//Make sure you actually add the file to the database.
			//Keep the user informed!
			
		});
		
		menu.addOption("View Finances", () -> {
			System.out.println("Bank routing number: "+ creator.getBankRouting());
			System.out.println("Bank account number: "+ creator.getBankAccountNumber());
			System.out.println("Total plays: "+ creator.getTotalPlays());
			System.out.println("Payment: "+ creator.getPayment());
			//TODO
			//What finances do they need to see?
			//Admin has a static variable for how much $$ is received per view.
			
		});
		
		menu.addOption("Back", () -> {
			changeScene(USER_SCENE);
			//TODO
			//Where do they go when they want to go back?
			
		});
		
	}
	

	private boolean validateFile(File file) {
		getFileType(file);
		
		if(file.exists()){
			return true;
		}
		char a = getFileType(file);
		if(a==Media.TYPE_IMAGE){
			return true;
		}
		if(a==Media.TYPE_VIDEO){
			return true;
		}
		
		if(a==Media.TYPE_MUSIC){
			return true;
		}
		//TODO
		//Does the file exist?
		//Is it a file we support? (Image, Video, or Music) (look at the extension!)
		return false;
		
	}
	
	private char getFileType(File file) {
		String s=file.getName();
		s =s.substring(s.lastIndexOf("."), s.length());
		switch (s){
		
		case "png": return Media.TYPE_IMAGE;
		
		case "mp3": return Media.TYPE_MUSIC;
		
		case "mp4": return Media.TYPE_VIDEO;
		
		
		
		}
		//TODO
		//What type of file is it?
		return Media.TYPE_UNKNOWN;
		
	}

	@Override
	public void onStart() {
		
		creator = new Creator(currentUser);
		
		curMenu = menu;
		while (true) {
			
			curMenu.print();
			curMenu.getInput(MediaCenterApplication.INPUT_SCANNER);
			System.out.println();
			
		}
		//What happens when this scene starts up?
		
	}

	@Override
	public void onExit() {
		
		//TODO
		//What happens when this scene exits?
		
	}

}