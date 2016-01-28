package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DownloadManagerGUI extends Application {

	public void start(Stage primaryStage) {

		BorderPane borderPane = new BorderPane();

		// Create the stage and main pane
		primaryStage.setTitle("JPL Download Manager");
		Scene scene = new Scene(borderPane, 1100, 800);
		
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());	
		
		// For Items on the topPane of Border Pane
		VBox topPane = new VBox();
		
		MenuBarNode menu = new MenuBarNode();
		ToolBarNode toolBar = new ToolBarNode();
		
		// Add the items to the topPane
		topPane.getChildren().addAll(menu, toolBar);
		
		// Set top pane to the borderPane layout
		borderPane.setTop(topPane);
		
		// Add the scene to the stage
		primaryStage.setScene(scene);
		
		// Show the stage
		primaryStage.show();

	}
	/**
	 * The main method is only needed for the IDE with limited
	 * JavaFX support. Not needed for running from the command line.
	 */
	public static void main(String[] args){
		Application.launch(args);
	}
}


