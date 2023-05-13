package uk.qmul.learningjourney;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PositionApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(uk.qmul.learningjourney.LoginApplication.class.getResource("position.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Achievement");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
