package gui;

import com.github.axet.wget.Downloader;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class class is responsible for running the GUI
 * 
 * @author Mariah Martinez
 * @author Adrian Rendon
 * 
 */

public class DownloadManagerGUI extends Application {

	private ReadOnlyDoubleProperty sceneHeight;
	private ReadOnlyDoubleProperty sceneWidth;
	private ReadOnlyDoubleProperty paneWidth;
	Downloader downloader;
	private User user;
	private ToolBarNode toolBar;
	private SummaryNode summary;
	
	public void start(Stage primaryStage) throws Exception {
		
		downloader = new Downloader();
		user = new User("jdoe", "password", "jdoe@calstatela.edu", "John Doe");

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

		MenuBarNode menu = new MenuBarNode(user, downloader);
		menu.getStyleClass().add("background");

		toolBar = new ToolBarNode();

		// Add the items to the topPane
		topPane.getChildren().addAll(menu, new Separator(), toolBar, new Separator());
		// Set top pane to the borderPane layout
		borderPane.setTop(topPane);
		summary = new SummaryNode();
		
		// Set center pane to table views
		borderPane.setCenter(createCenter(toolBar, menu));
		
		// Items in the bottom pane - Item description
		VBox bottomPane = new VBox();
		bottomPane.setSpacing(10);
		
		// Add summary to the bottomPane
		bottomPane.getChildren().addAll(new Separator(), summary);
		

		// Set bottom pane to the borderPane layout
		borderPane.setBottom(bottomPane);
		bottomPane.setPrefHeight(300);

		// Add the scene to the stage
		primaryStage.setScene(scene);

		// Show the stage
		primaryStage.show();

	}

	public HBox createCenter(ToolBarNode toolBar, MenuBarNode menuBar) throws Exception {
		HBox center = new HBox();
		center.prefWidthProperty().bind(paneWidth);	
		
		DatabaseTable database = new DatabaseTable(summary);
		database.prefWidthProperty().bind(paneWidth.divide(2));
		
		toolBar.setDatabaseTable(database);
		database.setMenuBar(menuBar);
		
		// Create downloads table
		DownloadsTable downloads = new DownloadsTable(downloader, database);
		downloads.createTable();
		downloads.prefWidthProperty().bind(paneWidth.divide(2));
		this.toolBar.setTable(downloads);

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


