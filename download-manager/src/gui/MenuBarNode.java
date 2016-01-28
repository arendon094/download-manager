package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarNode extends MenuBar {
		
	public MenuBarNode() {
		createMenu();
	}
	
	private MenuBar createMenu() {
		
		addFileMenu();
		addSettingsMenu();
		addUsersMenu();
		
		return this;
	}

	private void addSettingsMenu() {
		Menu menuFile = new Menu("Settings");
		MenuItem settings = new MenuItem("Download Settings");
		
		settings.setOnAction(new EventHandler<ActionEvent>(){		
			@Override public void handle(ActionEvent e) {
				SettingsScene settingsScene = new SettingsScene();
				PopUp settingsStage = new PopUp("Settings", settingsScene.getScene());
				settingsStage.show();
			}
		});
		
		menuFile.getItems().addAll(settings);
		this.getMenus().addAll(menuFile);
		
	}

	private void addFileMenu() {
		Menu menuFile = new Menu("File");
		MenuItem exit = new MenuItem("Exit");
		
		exit.setOnAction(new EventHandler<ActionEvent>(){		
			@Override public void handle(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuFile.getItems().addAll(exit);
		this.getMenus().addAll(menuFile);
	}
	
	private void addUsersMenu() {
		Menu menuUsers = new Menu("Users");
		MenuItem login = new MenuItem("Login");
		
		login.setOnAction(new EventHandler<ActionEvent>(){		
			@Override public void handle(ActionEvent e) {
				LoginScene loginScene = new LoginScene();
				PopUp loginStage = new PopUp("Login", loginScene.getScene());
				loginStage.show();
				
			}
		});
		
		menuUsers.getItems().addAll(login);
		this.getMenus().addAll(menuUsers);
		
	}

}
