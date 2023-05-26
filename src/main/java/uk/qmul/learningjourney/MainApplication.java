package uk.qmul.learningjourney;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application entrance
 */
public class MainApplication extends Application {

    /**
     * @param args launch arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param stage stage
     * @throws Exception Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Context.initialize(stage);
    }
}
