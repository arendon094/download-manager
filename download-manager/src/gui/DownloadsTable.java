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
	}

	private void createTable() {
		// Need to add type to tableview once we create
		// class for data
		TableView downloadsTable = new TableView();

		Label downloadLabel = new Label("My Downloads");
		downloadLabel.setFont(new Font("Calibri", 15));
		this.setAlignment(Pos.TOP_CENTER);

		downloadsTable.setEditable(true);

		TableColumn fileName = new TableColumn("File Name");
		TableColumn size = new TableColumn("Size");
		TableColumn downloaded = new TableColumn("Downloaded");

		downloadsTable.getColumns().addAll(fileName, size, downloaded);

		this.setSpacing(5);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().addAll(downloadLabel, downloadsTable);
	}

}
