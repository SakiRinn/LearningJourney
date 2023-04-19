package uk.qmul.learningjourney;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class LoginApplication extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 720, 449);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QM+ Log in ");
        primaryStage.setResizable(false); //登录窗口的大小不允许改变
        primaryStage.show();
    }
}




