package com.group13.main;
import com.group13.scenes.AdminScene;
import com.group13.scenes.CreatorScene;
import com.group13.scenes.ImageScene;
import com.group13.scenes.LoginScene;
import com.group13.scenes.MusicScene;
import com.group13.scenes.VideoScene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MediaCenterApplication extends Application {

	public static void main(String[] args) {
		MediaCenterApplication.launch(args);
	}
	
	private VideoScene _videoScene;
	private ImageScene _imageScene;
	private MusicScene _musicScene;
	private AdminScene _adminScene;
	private CreatorScene _creatorScene;
	private LoginScene _loginScene;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		//call scene constructors, giving each constructor a pane
		
	}
	
}