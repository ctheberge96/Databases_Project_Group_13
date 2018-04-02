package com.group13.scenes;
import java.io.File;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This scene is for an image.
 * <br>This scene has the ability to load an image
 * for viewing.
 * 
 * @author Conner Theberge
 */
public class ImageScene extends Scene {
	
	public ImageScene(Stage stage, Parent parent) {
		super(parent);
	}
	
	/**
	 * Loads an image from the given file into the
	 * image viewing nodes in this Scene.
	 * <br>Should be called directly before changing scene.
	 * <br>This might throw Exceptions if the file does not exist.
	 * 
	 * @param imageFile The file containing the image.
	 */
	public void loadImage(File videoFile) {
		//TODO stub
	}

}