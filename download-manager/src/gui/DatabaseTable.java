package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DatabaseTable extends VBox {
	
	public DatabaseTable() {
		createTable();
	}
	
	private void createTable(){
		// Need to add type to tableview once we create
		// class for data
		TableView dbTable = new TableView();

		Label dbLabel = new Label("JPL Database");
		dbLabel.setFont(new Font("Calibri", 15));
		this.setAlignment(Pos.TOP_CENTER);

		dbTable.setEditable(true);

		TableColumn fileName = new TableColumn("File Name");
		TableColumn size = new TableColumn("Size");
		TableColumn status = new TableColumn("Status");
		TableColumn speed = new TableColumn("speed");
		TableColumn remaining = new TableColumn("Time Remaining");
		TableColumn created = new TableColumn("Created");
		TableColumn encrypted = new TableColumn("Encrypted?");

		dbTable.getColumns().addAll(fileName, size, status, speed, remaining, created, encrypted);
		
		this.setSpacing(5);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().addAll(dbLabel, dbTable);	
	}
}
