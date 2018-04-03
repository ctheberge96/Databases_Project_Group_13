package com.group13.main;
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