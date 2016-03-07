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
	
	private String randDescription = "a large, bowl-shaped cavity in the ground or on the surface of a "
			+ "planet or the moon.";
	
	public SummaryNode(){
		createSummary();
	}
	
	private GridPane createSummary(){ // grid will be 5 rows by 7 columns
		this.setHgap(50); // Horizontal padding on grid
		this.setVgap(5); // vertical padding on grid
		this.setPadding(new Insets(0, 150, 5, 150)); // padding on all 4 sides of grid
		
		// label in row 1 columns 1
		Label fnLabel = new Label("File Name: ");
		this.add(fnLabel, 0, 0);
		
		// label in row 1 column 2-3
		Label fileName = new Label("<Name>");
		this.add(fileName, 1, 0, 2, 1);
		
		// link in row 2 columns 1-3
		Hyperlink link = new Hyperlink();
		link.setText("<The link to the original>");
		link.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		        // Action for when link is clicked
		    }
		});
		this.add(link, 0, 1, 3, 1);
		
		// image in rows 3-4 columns 1-3
		Image img = new Image("images/craters.jpg");
		ImageView craters = new ImageView(img);
		craters.setFitHeight(200);
		craters.setFitWidth(200);
		this.add(craters, 0, 2, 3, 3);
		
		// resume download button row 1 column 4-5
		Button resumeBtn = new Button();
        resumeBtn.setText("Resume Download");
        resumeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
        });
        this.add(resumeBtn, 3, 0, 2, 1);
		
		// paused download button row 1 columns 6
        Button pauseBtn = new Button();
        pauseBtn.setText("Pause Download");
        pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
        });
        this.add(pauseBtn, 5, 0);
		
		// remove download button row 1 column 7
        Button removeBtn = new Button();
        removeBtn.setText("Remove Download");
        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// button action goes here
			}
        });
        this.add(removeBtn, 6, 0);
        
        // description of image row 2 columns 4-7
        Text description = new Text(randDescription);
        description.setTextAlignment(TextAlignment.CENTER);
        this.add(description, 3, 1, 4, 1);
        
        // label size of image row 3 column 4
        Label sizeLabel = new Label("Size: ");
        this.add(sizeLabel, 3, 2);
        
        // size of image row 3 column 5
        Text fileSize = new Text("722 MB");
        this.add(fileSize, 4, 2);
        
        // label start time row 4 column 4
        Label startLabel = new Label("Download Start: ");
        this.add(startLabel, 3, 3);
        
        // start time row 4 column 5
        Text start = new Text("12:32 PM");
        this.add(start, 4, 3);
        
        // label finish time row 5 column 4
        Label finishLabel = new Label("Download Finish: ");
        this.add(finishLabel, 3, 4);
        
        // finish time row 5 column 5
        Text finish = new Text("--");
        this.add(finish, 4, 4);
        
        // progress bar row 3 columns 6-7
        ProgressBar progress = new ProgressBar(0.6);
        ProgressIndicator pIndicator = new ProgressIndicator(0.6);
        this.add(progress, 5, 2, 5, 1);
        this.add(pIndicator, 5, 2, 2, 1);
        
        // label time remaining row 4 columns 6-7
        Label timeLabel = new Label("Time Remaining: ");
        this.add(timeLabel, 5, 3, 2, 1);
        
        // time remaining row 4 columns 6-7
        Text timeRem = new Text("0 hours 57 minutes");
        this.add(timeRem, 6, 3, 2, 1);
		
		return this;
	}
	
}
