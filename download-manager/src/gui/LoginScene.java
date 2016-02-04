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
	
	public LoginScene() {
		createScene();
		addActions(this.scene);
	}

	private void addActions(Scene scene2) {
		// TODO Auto-generated method stub
		
	}
	
	public void createScene() {
        GridPane grid = new GridPane();
        this.scene = new Scene(grid, 350, 200);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(7);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
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
				//userTextField.getText();
				//pwBox.getText();
				//username and pass authentication
				actiontarget.setFill(Color.FIREBRICK);
		        actiontarget.setText("Wrong username or pass.");
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
	public Scene getScene() {
		return this.scene;
	}
}
