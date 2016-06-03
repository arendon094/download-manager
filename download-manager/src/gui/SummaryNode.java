package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.axet.wget.Downloader;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This class is responsible for display the summary of an item in the the main BottomPane
 * 
 * @author Adrian Rendon
 * 
 */

public class SummaryNode extends HBox {

	private ImageView craters;
	private Button resumeBtn;
	private VBox box;
	private HBox controlBox;

	public SummaryNode(){
		resumeBtn = new Button();
		box = new VBox();
		createBlankSummary();
	}

	private void createBlankSummary() {
		// Creates Blank summary: to be used when no item is selected
		
		this.setPadding(new Insets(25, 125, 25, 125)); // padding on all 4 sides of grid
		this.setAlignment(Pos.CENTER); 

		Text t = new Text(10, 50, "No image selected");
		controlBox = new HBox();
		controlBox.setSpacing(10);
		box.setSpacing(10);
		box.getChildren().add(t);
		t.getStyleClass().add("background");
		t.setFill(Color.WHITE);
		box.setAlignment(Pos.CENTER);
		box.setPrefHeight(300);

		this.getChildren().add(box);

	}

	private void createSummary(String filename, String desc, Image image){ // grid will be 6 rows by 5 columns

		// image in rows 1-6 columns 1-2
		this.getChildren().clear();
		this.box.getChildren().clear();
		this.controlBox.getChildren().clear();
		this.setSpacing(50);
		this.setAlignment(Pos.CENTER);
		this.box.setAlignment(Pos.CENTER_LEFT);

		Image img = new Image("images/default.png");
		craters = new ImageView(img);
		craters.setFitHeight(100);
		craters.setFitWidth(100);
		craters.preserveRatioProperty();

		// label in row 1 columns 3-4
		String nameLabel = "File Name: ";
		Text fileName = new Text(nameLabel + filename);
		fileName.setFill(Color.WHITE);

		this.box.getChildren().add(fileName);

		// link in row 2 columns 1-3
		Hyperlink link = new Hyperlink();
		link.setText("View on LMMP Website");
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// Action for when link is clicked
			}
		});
		//		this.add(link, 4, 0);
		this.box.getChildren().add(link);

		// description of image row 3 columns 3-5
		Text description = new Text("Description: " + desc);
		description.setFill(Color.WHITE);
		description.setTextAlignment(TextAlignment.CENTER);
		
		this.box.getChildren().add(description);

		// size of image row 4 column 3
		String sizeLabel = "Size: ";
		Text fileSize = new Text(sizeLabel + "722 MB");
		fileSize.setFill(Color.WHITE);
		
		this.box.getChildren().add(fileSize);


		// label start time row 5 column 3
		String startLabel = "Start Time: ";
		Text start = new Text(startLabel + "12:32 PM");
		
		start.setFill(Color.WHITE);

		// label finish time row 6 column 3
		String finishLabel = "Download Finish: ";
		Text finish = new Text(finishLabel + "--");
		finish.setFill(Color.WHITE);

		// progress indicator row 5 column 4
		Text progressLabel = new Text("Progress: ");
		progressLabel.setFill(Color.WHITE);
		ProgressBar pIndicator = new ProgressBar(0.0);
		
		HBox progress = new HBox(progressLabel, pIndicator);
		progress.setSpacing(15);
		this.box.getChildren().add(progress);

		// label time remaining row 6 columns 4-5
		String timeLabel = "Time Remaining: ";
		Text timeRem = new Text(timeLabel + "0 hours 57 minutes");
		timeRem.setFill(Color.WHITE);

		this.getChildren().addAll(craters, box);
		// resume download button row 2 column 3
		resumeBtn = new Button();
		resumeBtn.setText("Download");

		// paused download button row 2 column 4
		Button pauseBtn = new Button();
		pauseBtn.setText("Pause Download");
		pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
		});

		// remove download button row 2 column 4
		Button removeBtn = new Button();
		removeBtn.setText("Remove Download");
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
		});
		this.controlBox.getChildren().addAll(resumeBtn, pauseBtn, removeBtn);
		this.box.getChildren().add(controlBox);
	}

	public void showSummary(DownloadableFile file) {
		String description = file.getName().replace(".tif", "");
		this.createSummary(file.getName(), description, file.getImage());
	}

	public void updateImage(File file) {
		// Updates Image based on item selection
		Image image;
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
			image = SwingFXUtils.toFXImage(img, null);
			craters = new ImageView(image);
			craters.setFitHeight(300);
			craters.setFitWidth(200);
			craters.preserveRatioProperty();
		} catch (IOException e) {
			image = new Image("images/default.png");
			craters.setFitHeight(200);
			craters.setFitWidth(200);
			System.out.println("Image failed to load.");
		}

		//		this.add(craters, 0, 0, 2, 6);
		this.getChildren().set(0, craters);
	}

	public Button getDownloadButton() {
		return this.resumeBtn;
	}
}
