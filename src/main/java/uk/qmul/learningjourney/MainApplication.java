package uk.qmul.learningjourney;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-view.fxml"));
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Context.stage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 720, 449);
        stage.setScene(scene);
        stage.setTitle("QM+ Log in ");
        stage.setResizable(false); //登录窗口的大小不允许改变
        stage.show();
    }
}
