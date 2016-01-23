package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SettingsScene {
	
	private Scene scene;
	
	public SettingsScene() {
		createScene();
		addActions(this.scene);
	}

	private void addActions(Scene scene2) {
		// TODO Auto-generated method stub
		
	}

	private void createScene() {
		BorderPane borderPane = new BorderPane();
		
		this.scene = new Scene(borderPane, 400, 400);
		
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());	
		
		// For Items on the topPane of Border Pane
		VBox topPane = new VBox();
		
		Button close = new Button("Close");
		close.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        scene.getWindow().hide();
		    }
		});
		
		// Add the items to the topPane
		topPane.getChildren().add(close);
		
		// Set top pane to the borderPane layout
		borderPane.setTop(topPane);
	}
	
	public Scene getScene() {
		return this.scene;
	}
}
