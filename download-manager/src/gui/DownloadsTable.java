package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DownloadsTable extends VBox{

	public DownloadsTable() {
		createTable();
		this.getStylesheets().add("style/metroLight.css");
		this.getStyleClass().add("background");
	}

	private void createTable() {
		// Need to add type to tableview once we create
		// class for data
		TableView downloadsTable = new TableView();
		
		TableColumn downloads = new TableColumn("Downloads");
		
		TableColumn fileName = new TableColumn("File Name");
		TableColumn size = new TableColumn("Size");
		TableColumn status = new TableColumn("Status");
		TableColumn speed = new TableColumn("speed");
		TableColumn remaining = new TableColumn("Time Remaining");
		TableColumn created = new TableColumn("Created");
		TableColumn encrypted = new TableColumn("Encrypted?");
		
		downloads.getColumns().addAll(fileName, size, status, speed, created, encrypted);
		downloadsTable.getColumns().addAll(downloads);
		
		this.setSpacing(5);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().addAll(downloadsTable);
	}

}
