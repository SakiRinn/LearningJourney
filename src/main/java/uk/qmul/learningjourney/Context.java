package uk.qmul.learningjourney;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import uk.qmul.learningjourney.model.user.User;
import uk.qmul.learningjourney.util.DataIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Context {

    public static Stage stage;
    public static Scene homeScene;
    public static User user;

    public static Stack<Scene> sceneStack = new Stack<>();

    public static void initialize(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/login-view.fxml"));
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Context.stage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 720, 449);
        stage.setScene(scene);
        stage.setTitle("QM+ Log in ");
        stage.setResizable(false);
        stage.show();
    }

    public static boolean login(String id, String password) throws IOException {
        ArrayList<User> users = (ArrayList<User>) DataIO.loadObjects(User.class);
        assert users != null;
        for (User user : users) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                Context.user = user;
                return true;
            }
        }
        return false;
    }

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

    public static void toStudentHome() throws IOException {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/home/student-home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        homeScene = scene;
        sceneStack.add(scene);
        stage.setScene(scene);
        stage.show();
    }

    public static void toTeacherHome() throws IOException {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/home/teacher-home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        homeScene = scene;
        sceneStack.add(scene);
        stage.setScene(scene);
        stage.show();
    }

    public static void toHome() {
        sceneStack.clear();
        toNextScene(homeScene);
    }

    public static void toLastScene() {
        if (sceneStack.size() > 1) {
            sceneStack.pop();
            stage.setScene(sceneStack.peek());
        } else {
            stage.setScene(sceneStack.peek());
        }
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                alert.close();
            }
        });
    }

    public static void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                alert.close();
            }
        });
    }
}
