package gui;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DownloadManagerGUI extends Application {

	private ReadOnlyDoubleProperty sceneHeight;
	private ReadOnlyDoubleProperty sceneWidth;
	private ReadOnlyDoubleProperty paneWidth;

	public void start(Stage primaryStage) {

		BorderPane borderPane = new BorderPane();
		
		borderPane.getStylesheets().add("style/metroLight.css");
		borderPane.getStyleClass().add("background");

		// Create the stage and main pane
		primaryStage.setTitle("JPL Download Manager");
		Scene scene = new Scene(borderPane, 1100, 800);

		sceneHeight = scene.heightProperty();
		sceneWidth = scene.widthProperty();

		borderPane.prefHeightProperty().bind(sceneHeight);
		borderPane.prefWidthProperty().bind(sceneWidth);	

		paneWidth = borderPane.widthProperty();

		// For Items on the topPane of Border Pane
		VBox topPane = new VBox();

		MenuBarNode menu = new MenuBarNode();
		menu.getStyleClass().add("background");

		ToolBarNode toolBar = new ToolBarNode();

		// Add the items to the topPane
		topPane.getChildren().addAll(menu, new Separator(), toolBar, new Separator());

		// Set top pane to the borderPane layout
		borderPane.setTop(topPane);

		// Set center pane to table views
		borderPane.setCenter(createCenter());
		
		// Items in the bottom pane - Item description
		VBox bottomPane = new VBox();
		bottomPane.setSpacing(10);

		SummaryNode summary = new SummaryNode();
		
		Text t = new Text(10, 50, "No image selected");
		VBox v = new VBox(t);
		t.getStyleClass().add("background");
		t.setFill(Color.WHITE);
		v.setAlignment(Pos.CENTER);
		v.setPrefHeight(300);
	
		
		// Add item to the bottomPane
		bottomPane.getChildren().addAll(new Separator(), v);
		

		// Set bottom pane to the borderPane layout
		borderPane.setBottom(bottomPane);
		bottomPane.setPrefHeight(300);

		// Add the scene to the stage
		primaryStage.setScene(scene);

		// Show the stage
		primaryStage.show();

	}

	public HBox createCenter() {
		HBox center = new HBox();
		center.prefWidthProperty().bind(paneWidth);	

		DatabaseTable database = new DatabaseTable();
		database.prefWidthProperty().bind(paneWidth.divide(2));

		// Create downloads table
		DownloadsTable downloads = new DownloadsTable();
		downloads.prefWidthProperty().bind(paneWidth.divide(2));

		center.getChildren().addAll(database, downloads);
		return center;
	}

	/**
	 * The main method is only needed for the IDE with limited
	 * JavaFX support. Not needed for running from the command line.
	 */
	public static void main(String[] args){
//		Application.setUserAgentStylesheet("style/metroLight.css");
		Application.launch(args);
	}
}


