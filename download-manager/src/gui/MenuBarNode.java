package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.github.axet.wget.Downloader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarNode extends MenuBar {
	private String directory;
	private DatabaseTable dbTable;
	SettingsScene settingsScene;
	private User user;
	private Downloader downloader;
	
	public MenuBarNode(User user, Downloader downloader) {
		this.downloader = downloader;
		this.user = user;
		settingsScene = new SettingsScene(downloader);
		createMenu();
	}


	private MenuBar createMenu() {
		
		addFileMenu();
		addSearch();
		addSettingsMenu();
		addUsersMenu();
		
		return this;
	}

	private void addSettingsMenu() {
		Menu menuFile = new Menu("Settings");
		MenuItem settings = new MenuItem("Download Settings");
		
		settings.setOnAction(new EventHandler<ActionEvent>(){		
			@Override public void handle(ActionEvent e) {
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
	
	// Search label to be added to the menu bar
	private void addSearch(){
		Menu menuFile = new Menu("Search");
		MenuItem advancedS = new MenuItem("Advanced Search");
		
		// Advanced Search Pop Up goes here
		advancedS.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				// Button action goes here
			}
		});
		
		menuFile.getItems().addAll(advancedS);
		this.getMenus().addAll(menuFile);
	}

	private void addUsersMenu() {
		Menu menuUsers = new Menu("Users");
		MenuItem login = new MenuItem("Login");
		MenuItem profile = new MenuItem("Profile");
		
		login.setOnAction(new EventHandler<ActionEvent>(){		
			@Override public void handle(ActionEvent e) {
				LoginScene loginScene = new LoginScene("login", user);
				PopUp loginStage = new PopUp("Login", loginScene.getScene());
				loginStage.show();
				
			}
		});
		
		profile.setOnAction(new EventHandler<ActionEvent>(){		
			@Override public void handle(ActionEvent e) {
				LoginScene profileScene = new LoginScene("profile", user);
				PopUp profileStage = new PopUp("Profile", profileScene.getScene());
				profileStage.show();
				
			}
		});
		
		menuUsers.getItems().addAll(login, profile);
		this.getMenus().addAll(menuUsers);
		
	}
	public void setDatabaseTable(DatabaseTable dbTable) { 
		this.dbTable = dbTable;
	}
	public String getDirectory() {
		return settingsScene.getDirectory();
	}
}
