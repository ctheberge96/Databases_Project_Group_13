package com.group13.scenes;
import java.io.File;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This scene is for a song.
 * <br>This scene has the ability to load a song
 * for listening.
 * 
 * @author Conner Theberge
 */
public class MusicScene extends Scene {
	
	/**
	 * Loads the MusicScene, adding needed nodes
	 * to the given Parent.
	 * 
	 * @param parentPane The parent for this Scene
	 */
	public MusicScene(Stage stage, Pane parent) {
		super(parent);
	}
	
	/**
	 * Loads a song from the given file into the
	 * song viewing nodes in this Scene.
	 * <br>Should be called directly before changing scene.
	 * 
	 * @param musicFile The file containing the music.
	 */
	public void loadMusic(File musicFile) { 
		//TODO stub
	}

}