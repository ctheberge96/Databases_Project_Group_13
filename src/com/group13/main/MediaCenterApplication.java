package com.group13.main;
import java.io.File;

import com.group13.scenes.AdminScene;
import com.group13.scenes.CreatorScene;
import com.group13.scenes.ImageScene;
import com.group13.scenes.LoginScene;
import com.group13.scenes.MusicScene;
import com.group13.scenes.VideoScene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MediaCenterApplication extends Application {

	public static void main(String[] args) {
		MediaCenterApplication.launch(args);
	}
	
	public static VideoScene _videoScene;
	public static ImageScene _imageScene;
	public static MusicScene _musicScene;
	public static AdminScene _adminScene;
	public static CreatorScene _creatorScene;
	public static LoginScene _loginScene;
	
	private static Stage _stage;
	
	/**
	 * Sets the current scene to the given one and displays it.
	 */
	public static void changeScene(Scene scene) {
		
		_stage.setScene(scene);
		_stage.show();
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		_stage = stage;
		
		//call scene constructors, giving each constructor a pane
		_videoScene = new VideoScene(stage,new Pane());
		_imageScene = new ImageScene(stage,new Pane());
		_musicScene = new MusicScene(stage,new Pane());
		_adminScene = new AdminScene(stage,new Pane());
		_creatorScene = new CreatorScene(stage,new Pane());
		_loginScene = new LoginScene(stage,new Pane());
	
		stage.setScene(_loginScene); //Replace with respective scene for testing
		
		stage.setResizable(false);
		stage.setTitle("Media Center");
		stage.show();
		
	}
	
}