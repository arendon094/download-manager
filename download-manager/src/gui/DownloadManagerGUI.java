package gui;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DownloadManagerGUI extends Application {
	
	private ReadOnlyDoubleProperty sceneHeight;
	private ReadOnlyDoubleProperty sceneWidth;
	private ReadOnlyDoubleProperty paneWidth;

	public void start(Stage primaryStage) {

		BorderPane borderPane = new BorderPane();

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
		ToolBarNode toolBar = new ToolBarNode();
		
		// Add the items to the topPane
		topPane.getChildren().addAll(menu, toolBar);
		
		// Set top pane to the borderPane layout
		borderPane.setTop(topPane);
		
		// Set center pane to table views
		borderPane.setCenter(createCenter());
		
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
		Application.launch(args);
	}
}


