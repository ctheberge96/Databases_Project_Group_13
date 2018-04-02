package com.group13.scenes;
import java.io.File;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This scene is for a video.
 * <br>This scene has the ability to load a video
 * for viewing.
 * 
 * @author Conner Theberge
 */
public class VideoScene extends Scene {
	
	/**
	 * Loads the VideoScene, adding needed nodes
	 * to the given Parent.
	 * 
	 * @param parentPane The parent for this Scene
	 */
	public VideoScene(Stage stage, Parent parent) {
		super(parent);
	}
	
	/**
	 * Loads a video from the given file into the
	 * video viewing nodes in this Scene.
	 * <br>Should be called directly before changing scene.
	 * 
	 * @param videoFile The file containing the video.
	 */
	public void loadVideo(File videoFile) {
		//TODO stub
	}

}