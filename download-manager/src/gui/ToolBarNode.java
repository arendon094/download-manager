package gui;

import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.wget.Downloader;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.URLInfo;
import com.github.axet.wget.info.URLInfo.States;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolBarNode extends ToolBar{
	private DatabaseTable dbTable;
	private Downloader downloader;
	AtomicBoolean stopAtomic = new AtomicBoolean(false);
	private DownloadsTable table;
	
	public ToolBarNode(Downloader downloader) {
		this.downloader = downloader;

		createBar();
		DatabaseTable dbTable;
	}
	
	private ToolBar createBar() {
		
		this.setPrefHeight(30);
		this.getStyleClass().add("background");
		
		addButtons();
		addSearchBar();
		
		return this;
	}

	private void addButtons() {
		
		// Note all the icons are 16px
		Image resumeImage = new Image("images/play-16.png");
		Image pauseImage = new Image("images/pause-16.png");
		Image stopImage = new Image("images/stop-16.png");
		Image upImage = new Image("images/up-16.png");
		Image downImage = new Image("images/down-16.png");
		Image deleteImage = new Image("images/delete-16.png");

		Button resume = new Button();
		resume.setTooltip(new Tooltip("Resume"));
		resume.setGraphic(new ImageView(resumeImage));
		resume.getStyleClass().add("button-icon");
		
//		resume.setOnAction((event) -> {
//			Downloader downloader = new Downloader();
//			downloader.run();
//			System.out.println("running.....?");
//		});
		
		Button pause = new Button();
		pause.setTooltip(new Tooltip("Pause"));
		pause.setGraphic(new ImageView(pauseImage));
		pause.getStyleClass().add("button-icon");

		Button stop = new Button();
		stop.setTooltip(new Tooltip("Stop"));
		stop.setGraphic(new ImageView(stopImage));
		stop.getStyleClass().add("button-icon");
		
		stop.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	table.deleteSelected();
		    }
		});
		
		Button up = new Button();
		up.setTooltip(new Tooltip("up"));
		up.setGraphic(new ImageView(upImage));
		up.getStyleClass().add("button-icon");
	
		Button down = new Button();
		down.setTooltip(new Tooltip("down"));
		down.setGraphic(new ImageView(downImage));
		down.getStyleClass().add("button-icon");
		
		Button delete = new Button();
		delete.setTooltip(new Tooltip("delete"));
		delete.setGraphic(new ImageView(deleteImage));
		delete.getStyleClass().add("button-icon");

		this.getItems().addAll(resume, pause, stop, up, down, delete);
	}
	
	private void addSearchBar(){
		Image mGlassImage = new Image("images/search-16.png");
		
		final TextField searchField = new TextField("");
        searchField.setPromptText("Search");
        
        Button mglass = new Button();
		mglass.setTooltip(new Tooltip("Search"));
		mglass.setGraphic(new ImageView(mGlassImage));
		mglass.getStyleClass().add("button-icon");
		
		mglass.setOnAction((event) -> {
			dbTable.updateFilter(searchField.getText());
		});

		// Moves position of both search field and button by 650 to the right
		searchField.setTranslateX(450);
		mglass.setTranslateX(450);

		// Positions text inside the search field to the right
		searchField.setAlignment(Pos.CENTER_RIGHT);
        
        this.getItems().addAll(searchField, mglass);
	}
	
	public void setDatabaseTable(DatabaseTable dbTable) {
		this.dbTable = dbTable;
	}

	public void setTable(DownloadsTable table) {
		this.table = table;
	}
}
