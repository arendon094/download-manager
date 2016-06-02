package gui;

import java.io.File;

import com.github.axet.wget.Downloader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;

public class SettingsScene {
	boolean selected = false;
	private Scene scene;
	private File newDirectory;
	private File directory;
	Downloader downloader;
	
	public SettingsScene(Downloader downloader) {
		directory = new File(System.getProperty("user.home") + "/Downloads");
		newDirectory = directory;
		this.downloader = downloader;
		createScene();
		addActions(this.scene);
	}

	private void addActions(Scene scene2) {
		// TODO Auto-generated method stub

	}

	private void createScene() {
		FlowPane root = new FlowPane(Orientation.HORIZONTAL, 5, 5);
		root.setPadding(new Insets(5));
		this.scene = new Scene(root, 450, 450);
		root.getStylesheets().add("style/metroLight.css");
        root.getStyleClass().add("background");

		// directory button
		Button btnOpenDirectoryChooser = new Button("...");
		GridPane.setConstraints(btnOpenDirectoryChooser, 1, 0);

		// Labels needed
		Label Dsl = new Label("Down speed limit (KB/sec):");

		Label Usl = new Label("Up speed limit: (KB/sec):");
		Usl.setPadding(new Insets(0, 10, 0, 0));

		Label activeDown = new Label("Maximum active download:");
		activeDown.setPadding(new Insets(0, 0, 0, 30));
		Label activeUp = new Label("Maximum active upload:");
		activeUp.setPadding(new Insets(0, 0, 0, 30));

		Label limit = new Label("Limit active transfer count");
		limit.setFont(Font.font("", FontWeight.BOLD, 12));

		Label Destination = new Label("Destination directory: ");
		Destination.setPadding(new Insets(5, 20, 0, 0));

		Label scheduleTxt=new Label("Schedule download:");
		scheduleTxt.setPadding(new Insets(5,20,0,0));

		// checkbox for active downloads/uploads
		CheckBox checkBox = new CheckBox();

		// Define Textfieds
		TextField DestTxt = new TextField();
		DestTxt.setText(directory.getAbsolutePath());
		DestTxt.setPrefWidth(220);
		TextField time=new TextField();
		time.setText("00:00:00");
		time.setPrefWidth(150);



		// Hbox for directory
		HBox hBox = new HBox();
		hBox.setPrefWidth(450);
		hBox.setPadding(new Insets(10, 0, 0, 0));
		// hBox.setStyle("-fx-background-color: BLACK;");

		// Hbox for the download speed limit
		HBox speedBox = new HBox(8);
		speedBox.setPrefWidth(350);
		speedBox.setPadding(new Insets(25, 12, 15, 0));

		// Hbox for upload speed limit
		HBox speedBox1 = new HBox(8);
		speedBox1.setPrefWidth(350);
		speedBox1.setPadding(new Insets(25, 12, 15, 0));

		// Hbox will for contain checkbox
		HBox limitActive = new HBox(8);
		limitActive.setPrefWidth(350);

		// Hbox for max active download
		HBox limitChoices = new HBox(8);
		limitChoices.setPrefWidth(350);
		limitChoices.setPadding(new Insets(0, 12, 5, 0));

		// Hbox for max active upload
		HBox limitChoices1 = new HBox(8);
		limitChoices1.setPrefWidth(350);
		limitChoices1.setPadding(new Insets(0, 12, 5, 0));

		//Hbox for schuduler
		HBox schedulerBox=new HBox(8);
		schedulerBox.setPrefWidth(350);
		schedulerBox.setPadding(new Insets(15,12,5,0));

		// Declaring the spinners
		Spinner spinnerUp = new Spinner();

		spinnerUp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
		spinnerUp.setEditable(true);
		//System.out.println(spinnerUp.getValue());
		spinnerUp.setPrefWidth(75);

		Spinner spinnerD = new Spinner();

		spinnerD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
		spinnerD.setEditable(true);
		spinnerD.setPrefWidth(75);

		Spinner activeDspin = new Spinner();

		activeDspin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
		activeDspin.setEditable(true);
		activeDspin.setPrefWidth(75);

		Spinner spinnerUspin = new Spinner();

		spinnerUspin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
		spinnerUspin.setEditable(true);
		spinnerUspin.setPrefWidth(75);

		// Button action, let user choose directory
		btnOpenDirectoryChooser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setInitialDirectory(directory);
				newDirectory = directoryChooser.showDialog(null);

				if (newDirectory == null) {
					DestTxt.setText(directory.getAbsolutePath());
				} else {
					DestTxt.setText(newDirectory.getAbsolutePath());
				}
			}
		});
		
		//save directory change and exit popup
		Button saveButton = new Button("Save");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				directory = newDirectory;
				downloader.setDirectory(newDirectory.getAbsolutePath());
				getScene().getWindow().hide();
				
			}
			
		});
		//revert any changes to directory to original and exit popup
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				newDirectory = directory;
				DestTxt.setText(directory.getAbsolutePath());
				getScene().getWindow().hide();
			}
			
		});
		
		// add each node respectively
		hBox.getChildren().add(Destination);
		hBox.getChildren().addAll(DestTxt, btnOpenDirectoryChooser);
		speedBox.getChildren().addAll(Dsl, spinnerUp);
		speedBox1.getChildren().addAll(Usl, spinnerD);
		limitActive.getChildren().addAll(checkBox, limit);
		limitChoices.getChildren().addAll(activeDown, activeDspin);
		limitChoices1.getChildren().addAll(activeUp, spinnerUspin);
		schedulerBox.getChildren().addAll(scheduleTxt,time);
		// Set the nodes to the root to display
		root.getChildren().add(hBox);
		root.getChildren().add(speedBox);
		root.getChildren().add(speedBox1);
		root.getChildren().add(limitActive);
		root.getChildren().add(limitChoices);
		root.getChildren().add(limitChoices1);
		root.getChildren().add(schedulerBox);
		
		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(saveButton);
		buttonBox.getChildren().add(cancelButton);
		root.getChildren().add(buttonBox);
	}
	public Scene getScene() {
		return this.scene;
	}
	public String getDirectory() {
		return directory.getAbsolutePath();
	}
}
