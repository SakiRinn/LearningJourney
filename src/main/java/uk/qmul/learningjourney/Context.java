package uk.qmul.learningjourney;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class Context {
    private static Context context;
    public static Stage stage;

    public static HashMap<String, Object> controllers = new HashMap<>();
    public static Scene homeScene;
    public static Stack<Scene> sceneStack = new Stack<>();

    public static void toNextScene(Scene scene) {
        sceneStack.add(scene);
        stage.setScene(scene);
    }

    public static void toNextScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        sceneStack.add(scene);
        stage.setScene(scene);
    }

    public static void toNextScene(String fxml, double v, double v1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), v, v1);
        sceneStack.add(scene);
        stage.setScene(scene);
    }

    public static void newStage(String fxml, double v, double v1, String title) throws IOException {
        stage.close();
        toNextScene(fxml, v, v1);
        stage.setTitle(title);
        stage.setResizable(false); //登录窗口的大小不允许改变
        stage.show();
    }

    public static void setHomeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-home-view.fxml"));
        homeScene = new Scene(fxmlLoader.load(), 800, 600);
    }

    public static void toLastScene() {
        if (!sceneStack.empty()) {
            sceneStack.pop();
            if (!sceneStack.empty())
                stage.setScene(sceneStack.pop());
        }
    }
}
