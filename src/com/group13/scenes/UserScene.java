package com.group13.scenes;

import java.util.LinkedList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserScene extends Scene {

	public UserScene(Stage stage, Pane parent) {
		super(parent);
		
		Button btnAccount = new Button("Account Settings");
		
		TextArea searchBar = new TextArea();
		searchBar.setPrefSize(300, 20);
		
		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(value -> {
			
			searchForString(searchBar.getText());
			
		});
		
		Button btnFavs = new Button("Favorites");
		Button btnFollowed = new Button("Followed Creators");
		Button btnAll = new Button("All");
		
		ScrollPane videoScroller = new ScrollPane();
		videoScroller.setPrefSize(640, 480);
		
		HBox header = new HBox(btnAccount, btnSearch, searchBar);
		header.setAlignment(Pos.CENTER);
		header.setSpacing(16);
		header.setPadding(new Insets(12));
		
		VBox contentSortControls = new VBox(btnFavs, btnFollowed, btnAll);
		contentSortControls.setAlignment(Pos.CENTER);
		contentSortControls.setSpacing(16);
		
		HBox mediaContent = new HBox(videoScroller, contentSortControls);
		mediaContent.setAlignment(Pos.CENTER);
		mediaContent.setSpacing(16);
		mediaContent.setPadding(new Insets(12));
		
		VBox container = new VBox(header, mediaContent);
		container.setAlignment(Pos.CENTER);
		container.setSpacing(16);
		
		parent.getChildren().add(container);
		
	}
	
	private LinkedList<Integer> searchForString(String search) {
		return null;
		
	}

}