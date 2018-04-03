package com.group13.scenes;
import java.io.File;

import javafx.scene.Parent;
import javafx.scene.Scene;
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
	public VideoScene(Stage stage, Parent parent) {
		super(parent);
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
		videoPlayer.setVolume(0);
		videoPlayer.play();
		videoPlayer.seek(seekTime);
		videoPlayer.stop();
		videoPlayer.setVolume(1);
		
		_videoView.setMediaPlayer(videoPlayer);
		
	}
	
}