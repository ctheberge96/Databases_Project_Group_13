package com.group13.scenes;
import java.io.File;

import com.group13.main.MediaCenterApplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	public VideoScene(Stage stage, Pane parent) {
		super(parent);
		
		parent.setPrefSize(645, 460);
		
		VBox container = new VBox();
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(8));
		container.setSpacing(16);
		
		Button btnPlayPause = new Button("Play");
		btnPlayPause.setOnAction(event -> {
			
			if (_videoView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
				
				btnPlayPause.setText("Play"); //From playing to stopped
				_videoView.getMediaPlayer().pause();
				
			} else {
				
				btnPlayPause.setText("Pause");
				_videoView.getMediaPlayer().play();
				
			}
		
		});
		
		Button back = new Button("Back To Media Center");
		back.setOnAction(event -> {
			
			//TODO temporary until main scene!!!!
			MediaCenterApplication.changeScene(MediaCenterApplication._loginScene);
			
		});
		
		_videoView = new MediaView();
		_videoView.setFitWidth(640);
		_videoView.setFitHeight(480);
		container.getChildren().addAll(back, _videoView, btnPlayPause);
		
		parent.getChildren().add(container);
		
	}
	
	private MediaView _videoView;
	
	/**
	 * Loads a video from the given file into the
	 * video viewing nodes in this Scene.
	 * <br>Starts the video from the beginning.
	 * 
	 * @param videoFile The file containing the video.
	 */
	public void loadVideo(File videoFile) {
		
		loadVideo(videoFile, new Duration(0.0));
		
	}

	/**
	 * Loads a video from the given file into the
	 * video viewing nodes in this Scene.
	 * <br>Starts the video from the beginning.
	 * <br>Since MediaPlayer does not allow for seeking while stopped,
	 * this function starts the video, seeks the given duration, and
	 * stops the video.
	 * 
	 * @param videoFile The file containing the video.
	 * @param seekTime The {@link Duration} (position in the video) to start at
	 */
	public void loadVideo(File videoFile, Duration seekTime) {
		
		MediaPlayer videoPlayer = new MediaPlayer(new Media(videoFile.toURI().toString()));
		_videoView.setMediaPlayer(videoPlayer);
		
		int initialCount = videoPlayer.getCycleCount();

		videoPlayer.setOnReady(() -> {
			
			if (videoPlayer.getCycleCount() == initialCount) {
			
				videoPlayer.setVolume(0);
				videoPlayer.play();
				videoPlayer.seek(seekTime);
				
				videoPlayer.setCycleCount(initialCount + 1);
			
			}
						
		});
		
		videoPlayer.cycleCountProperty().addListener(observer -> {
			
			if (videoPlayer.getCycleDuration().toSeconds() >= seekTime.toSeconds()) {
				
				videoPlayer.pause();
				
			} else {
				
				videoPlayer.setCycleCount(videoPlayer.getCycleCount() + 1);
				
			}
			
		});
		
	}
	
}