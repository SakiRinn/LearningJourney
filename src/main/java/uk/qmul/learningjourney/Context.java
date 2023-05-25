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


/**
 * Context switch for the application
 *
 * @author Zekai Liu
 * @date 2023/04/25
 */
public class Context {

    /**
     * stage
     */
    public static Stage stage;
    /**
     * home scene
     */
    public static Scene homeScene;
    /**
     * current user
     */
    public static User user;

    /**
     * a stack storing scenes
     */
    public static Stack<Scene> sceneStack = new Stack<>();

    /**
     * Initialize when application start
     *
     * @param stage stage
     * @throws IOException ioexception
     */
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

    /**
     * Login authentication
     *
     * @param id       id
     * @param password password
     * @return boolean whether the input credential is valid
     * @throws IOException ioexception
     */
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

    public static void logout() throws IOException {
        stage.close();
        sceneStack.clear();
        homeScene = null;
        user = null;
        initialize(stage);
    }

    /**
     * Go to the next scene
     *
     * @param scene scene
     */
    public static void toNextScene(Scene scene) {
        sceneStack.add(scene);
        stage.setScene(scene);
    }

    /**
     * Go to the next scene
     *
     * @param fxml fxml of the view
     * @throws IOException ioexception
     */
    public static void toNextScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        sceneStack.add(scene);
        stage.setScene(scene);
    }

    /**
     * Go to the next scene
     *
     * @param fxml fxml of the view
     * @param v    width
     * @param v1   height
     * @throws IOException ioexception
     */
    public static void toNextScene(String fxml, double v, double v1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), v, v1);
        sceneStack.add(scene);
        stage.setScene(scene);
    }

    /**
     * Go to student home scene
     *
     * @throws IOException ioexception
     */
    public static void toStudentHome() throws IOException {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/home/student-home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        homeScene = scene;
        sceneStack.add(scene);
        stage.setTitle("QM+ Student");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Go to teacher home scene
     *
     * @throws IOException ioexception
     */
    public static void toTeacherHome() throws IOException {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/home/teacher-home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        homeScene = scene;
        sceneStack.add(scene);
        stage.setTitle("QM+ Teacher");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Back to home scene
     */
    public static void toHome() {
        sceneStack.clear();
        toNextScene(homeScene);
    }

    /**
     * Back to the previous scene
     */
    public static void toLastScene() {
        if (sceneStack.size() > 1) {
            sceneStack.pop();
            stage.setScene(sceneStack.peek());
        } else {
            stage.setScene(sceneStack.peek());
        }
    }

    /**
     * Show an error message
     *
     * @param message message
     */
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

    /**
     * Show information
     *
     * @param message message
     */
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

    public static boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }
}
