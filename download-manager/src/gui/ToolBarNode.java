package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolBarNode extends ToolBar{
	
	public ToolBarNode() {
		createBar();
	}
	
	private ToolBar createBar() {
		
		this.setPrefHeight(30);
		this.getStylesheets().add("style/toolCSS.css");
		
		addButtons();
		
		return this;
	}

	private void addButtons() {
		
		// Note all the icons are 16px
		Image resumeImage = new Image("images/resume.png");
		Image pauseImage = new Image("images/pause.png");
		Image stopImage = new Image("images/stop.png");
		Image upImage = new Image("images/up.png");
		Image downImage = new Image("images/down.png");

		
		Button resume = new Button();
		resume.setTooltip(new Tooltip("Resume"));
		resume.setGraphic(new ImageView(resumeImage));

		
		Button pause = new Button();
		pause.setTooltip(new Tooltip("Pause"));
		pause.setGraphic(new ImageView(pauseImage));

		
		Button stop = new Button();
		stop.setTooltip(new Tooltip("Stop"));
		stop.setGraphic(new ImageView(stopImage));

		
		Button up = new Button();
		up.setTooltip(new Tooltip("up"));
		up.setGraphic(new ImageView(upImage));

		
		Button down = new Button();
		down.setTooltip(new Tooltip("down"));
		down.setGraphic(new ImageView(downImage));

		this.getItems().addAll(resume,new Separator(), pause,
				new Separator(), stop, new Separator(), up, new Separator(), down);
	}
}
