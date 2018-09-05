//package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//Yet to create verification for login.
//styling.
public class Main extends Application {

    static TextField username;
    static PasswordField password;
    public static Stage window;
    private Scene loginScene;


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Test your Knowledge..!!");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label label = new Label("Please Login to Continue.");
        GridPane.setConstraints(label,7,12);

        //login button
        Button loginbutton = new Button("Log In");
        //no access verification.
        loginbutton.setOnAction(event ->{
            if((username.getText().equals("amogh"))&&(password.getText().equals("ts1989"))){
                window.setScene(homeScreen.getScene());
            }
        });
        GridPane.setConstraints(loginbutton,7,15);

        //username
        username = new TextField();
        username.setPromptText("Username");
        GridPane.setConstraints(username,7,13);

        //password
        password = new PasswordField();
        password.setPromptText("Password");
        GridPane.setConstraints(password,7,14);

        grid.getChildren().addAll(loginbutton,username,password,label);
        loginScene = new Scene(grid,300,400);
        window.setScene(loginScene);
        window.show();
    }



    public static void main(String[] args) {
        launch(args);

    }



}
