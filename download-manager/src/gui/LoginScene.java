package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginScene {
	
	private Scene scene;
	private User user;
	private boolean loggedIn = false;
	
	public LoginScene(String type, User user) {
		this.user = user;
		if(type == "login") {
			createScene();
		}
		else if(type == "profile") {
			createProfileScene();
		}
		else if(type == "edit") {
			createEditScene();
		}
		addActions(this.scene);
	}

	private void addActions(Scene scene2) {
		// TODO Auto-generated method stub
		
	}
	
	private void createScene() {
        GridPane grid = new GridPane();
        this.scene = new Scene(grid, 500, 200);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(7);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.getStylesheets().add("style/metroLight.css");
        grid.getStyleClass().add("background");
        
        Label userNameLabel = new Label("User Name:");
        grid.add(userNameLabel, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pwLabel = new Label("Password:");
        grid.add(pwLabel, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 3);
        
        Button loginBtn = new Button();
        loginBtn.setText("Login");
        
        Button forgotBtn = new Button();
        forgotBtn.setText("Forgot name/pass?");
        
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!loggedIn && userTextField.getText().equals(user.getUsername()) && pwBox.getText().equals(user.getPassword())) {
					loggedIn = true;
					actiontarget.setFill(Color.GREEN);
					actiontarget.setText("Welcome, " + user.getUsername());
					loginBtn.setText("Logout");
					userTextField.setDisable(true);
					userTextField.setText("");
					pwBox.setDisable(true);
					pwBox.setText("");
				} else if(loggedIn){
					loggedIn = false;
					actiontarget.setFill(Color.GREEN);
					actiontarget.setText("You are now logged out.");
					loginBtn.setText("Login");
					userTextField.setDisable(false);
					pwBox.setDisable(false);
				} else {
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Wrong username or pass.");
				}
			}
        });
        
        forgotBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				actiontarget.setFill(Color.FIREBRICK);
		        actiontarget.setText("Too bad?");
			}
        });
        
        HBox hbBtn = new HBox(10);
	    hbBtn.setAlignment(Pos.BOTTOM_RIGHT); 
	    hbBtn.getChildren().add(forgotBtn);
	    hbBtn.getChildren().add(loginBtn);
	    grid.add(hbBtn, 1, 4);
	    
	}
	
	private void createProfileScene() {
        GridPane grid = new GridPane();
        this.scene = new Scene(grid, 400, 300);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(7);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.getStylesheets().add("style/metroLight.css");
        grid.getStyleClass().add("background");
        
        Label userNameLabel = new Label("User Name:");
        grid.add(userNameLabel, 0, 0);
        
        Text userName = new Text();
        //get username
        userName.setText(user.getUsername());
        userName.setFill(Color.WHITE);
        grid.add(userName, 1, 0);
        
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);
        
        Text name = new Text();
        name.setFill(Color.WHITE);
        //get name
        name.setText(user.getName());
        grid.add(name, 1, 1);
        
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        
        Text password = new Text();
        //get pass?
        password.setText("*****");
        password.setFill(Color.WHITE);
        grid.add(password, 1, 2);
        
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 3);
        
        Text email = new Text();
      //get email
        email.setText(user.getEmail());
        email.setFill(Color.WHITE);
        grid.add(email, 1, 3);
        
        Button editBtn = new Button();
        editBtn.setText("Edit Profile");

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginScene editScene = new LoginScene("edit", user);
				PopUp editStage = new PopUp("Edit Profile", editScene.getScene());
				editStage.show();
			}
        });
        
        Button exitBtn = new Button();
        exitBtn.setText("OK");

        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				scene.getWindow().hide();
			}
        });
        
        HBox hbBtn = new HBox(10);
	    hbBtn.setAlignment(Pos.BOTTOM_RIGHT); 
	    hbBtn.getChildren().add(editBtn);
	    hbBtn.getChildren().add(exitBtn);
	    grid.add(hbBtn, 1, 4);
	}
	private void createEditScene() {
        GridPane grid = new GridPane();
        this.scene = new Scene(grid, 400, 300);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(7);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.getStylesheets().add("style/metroLight.css");
        grid.getStyleClass().add("background");
        
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);
        
        TextField nameTextField = new TextField(user.getName());
        grid.add(nameTextField, 1, 1);
        
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        
        PasswordField pwField = new PasswordField();
        grid.add(pwField, 1, 2);
        
        Label rePasswordLabel = new Label("Re-enter Password:");
        grid.add(rePasswordLabel, 0, 3);
        
        PasswordField rePwField = new PasswordField();
        grid.add(rePwField, 1, 3);
        
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 4);
        
        TextField emailField = new TextField(user.getEmail());
        grid.add(emailField, 1, 4);
        
        Label reEmailLabel = new Label("Re-enter Email:");
        grid.add(reEmailLabel, 0, 5);
        
        TextField reEmailField = new TextField();
        grid.add(reEmailField, 1, 5);
        
        Button saveBtn = new Button();
        saveBtn.setText("Save");
        
        Button cancelBtn = new Button();
        cancelBtn.setText("Cancel");
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!(pwField.getText().equals(rePwField.getText()))) {
					actiontarget.setFill(Color.FIREBRICK);
			        actiontarget.setText("Password does not match");
				}
				else if(!(emailField.getText().equals(reEmailField.getText()))) {
					actiontarget.setFill(Color.FIREBRICK);
			        actiontarget.setText("Email does not match");
				}
				else {
					//save changes to account
					user.setEmail(emailField.getText());
					user.setName(nameTextField.getText());
					user.setPassword(pwField.getText());
					actiontarget.setFill(Color.GREEN);
			        actiontarget.setText("Profile Updated");
			        nameTextField.setText(user.getName());
			        emailField.setText(user.getEmail());
				}
			}
        });
        
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				scene.getWindow().hide();
			}
        });
        
        HBox hbBtn = new HBox(10);
	    hbBtn.setAlignment(Pos.BOTTOM_RIGHT); 
	    hbBtn.getChildren().add(saveBtn);
	    hbBtn.getChildren().add(cancelBtn);
	    grid.add(hbBtn, 1, 7);
	    
	}
	public Scene getScene() {
		return this.scene;
	}
}
