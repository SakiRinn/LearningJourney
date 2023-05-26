package uk.qmul.learningjourney.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import kotlin.NotImplementedError;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.util.UserUtil;

import java.io.IOException;

/**
 * The controller of the view file `user-register-view.fxml`.
 * <p>
 *     This interface reads the necessary information to create a new user
 *     and then adds the user to the database.
 * </p><p>
 *     Note that Only administrators have permission to access this page and use the functions.
 * </p>
 *
 * @author Lyuhua Wu
 * @see BaseController
 */
public class UserRegisterController extends BaseController {

    @FXML
    private ComboBox<String> selectBox;
    @FXML
    private Button registerButton;
    @FXML
    private AnchorPane studentPane;
    @FXML
    private AnchorPane teacherPane;

    @FXML
    private TextField studentId;
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentPassword;
    @FXML
    private TextField studentCollege;
    @FXML
    private TextField studentMajor;
    @FXML
    private TextField studentClassId;

    @FXML
    private TextField teacherId;
    @FXML
    private TextField teacherName;
    @FXML
    private TextField teacherPassword;
    @FXML
    private CheckBox isAdmin;

    @Override
    public void initialize() {
        selectBox.getItems().addAll("Student", "Teacher");
        selectBox.setValue("Student");
        selectBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Student")) {
                studentPane.setVisible(true);
                teacherPane.setVisible(false);
                registerButton.setOnMouseClicked(event -> registerStudent());
            } else if (newValue.equals("Teacher")) {
                studentPane.setVisible(false);
                teacherPane.setVisible(true);
                registerButton.setOnMouseClicked(event -> registerTeacher());
            } else
                throw new NotImplementedError();
        });
    }

    /**
     * Register a new student.
     */
    public void registerStudent() {
        if (!Context.showConfirmation("Have you confirmed all the information?"))
            return;
        try {
            UserUtil.saveUser(new Student(studentId.getText(), studentName.getText(), studentPassword.getText(),
                    studentCollege.getText(), studentMajor.getText(), studentClassId.getText()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Context.showInformation("Success to register a new student!");
        try {
            Context.toTeacherHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Register a new teacher.
     */
    public void registerTeacher() {
        if (!Context.showConfirmation("Have you confirmed all the information?"))
            return;
        try {
            UserUtil.saveUser(new Teacher(teacherId.getText(), teacherName.getText(), teacherPassword.getText(),
                    isAdmin.isSelected()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Context.showInformation("Success to register a new teacher!");
        try {
            Context.toTeacherHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
