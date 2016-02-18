package gui;

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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SummaryNode extends GridPane {
	
	private String randDescription = "a large, bowl-shaped cavity in the ground";
	
	public SummaryNode(){
		createSummary();
	}
	
	private GridPane createSummary(){ // grid will be 6 rows by 5 columns
		this.setHgap(50); // Horizontal padding on grid
		this.setVgap(5); // vertical padding on grid
		this.setPadding(new Insets(25, 125, 25, 125)); // padding on all 4 sides of grid
		this.setAlignment(Pos.CENTER);
		
		// image in rows 1-6 columns 1-2
		Image img = new Image("images/craters.png");
		ImageView craters = new ImageView(img);
		this.add(craters, 0, 0, 2, 6);
		
		// label in row 1 columns 3-4
		String nameLabel = "File Name: ";
		Label fileName = new Label(nameLabel + "<Name>");
		this.add(fileName, 2, 0, 2, 1);
		
		// link in row 2 columns 1-3
		Hyperlink link = new Hyperlink();
		link.setText("<The link to the original>");
		link.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		        // Action for when link is clicked
		    }
		});
		this.add(link, 4, 0);

		// resume download button row 2 column 3
		Button resumeBtn = new Button();
        resumeBtn.setText("Resume Download");
        resumeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
        });
        this.add(resumeBtn, 2, 1);
		
		// paused download button row 2 column 4
        Button pauseBtn = new Button();
        pauseBtn.setText("Pause Download");
        pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
        });
        this.add(pauseBtn, 3, 1);
		
		// remove download button row 2 column 4
        Button removeBtn = new Button();
        removeBtn.setText("Remove Download");
        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
        });
        this.add(removeBtn, 4, 1);
        
        // description of image row 3 columns 3-5
        Text description = new Text("Description: " + randDescription);
        description.setTextAlignment(TextAlignment.CENTER);
        this.add(description, 2, 2, 3, 1);
        
        // size of image row 4 column 3
        String sizeLabel = "Size: ";
        Text fileSize = new Text(sizeLabel + "722 MB");
        this.add(fileSize, 2, 3);
        
        // label start time row 5 column 3
        String startLabel = "Start Time: ";
        Text start = new Text(startLabel + "12:32 PM");
        this.add(start, 2, 4);
        
        // label finish time row 6 column 3
        String finishLabel = "Download Finish: ";
        Text finish = new Text(finishLabel + "--");
        this.add(finish, 2, 5);
        
        // progress indicator row 5 column 4
        Label progressLabel = new Label("Progress: ");
        this.add(progressLabel, 3, 4);
        
     // progress indicator row 5 column 5
        ProgressIndicator pIndicator = new ProgressIndicator(0.6);
        this.add(pIndicator, 4, 4);
        
        // label time remaining row 6 columns 4-5
        String timeLabel = "Time Remaining: ";
        Text timeRem = new Text(timeLabel + "0 hours 57 minutes");
        this.add(timeRem, 3, 5, 2, 1);
		
		return this;
	}
	
}
