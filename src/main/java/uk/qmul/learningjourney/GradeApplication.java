package uk.qmul.learningjourney;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GradeApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("grade-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 720, 458);
        stage.setTitle("Historical Grades");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
