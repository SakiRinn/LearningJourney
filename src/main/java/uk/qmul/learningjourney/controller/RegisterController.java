package uk.qmul.learningjourney.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import kotlin.NotImplementedError;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.util.UserUtil;

import java.io.IOException;

public class RegisterController extends BaseController {

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
                registerButton.setOnMouseClicked(event -> {
                    if (Context.showConfirmation("Have you confirmed all the information?"))
                        registerStudent();
                });
            } else if (newValue.equals("Teacher")) {
                studentPane.setVisible(false);
                teacherPane.setVisible(true);
                registerButton.setOnMouseClicked(event -> {
                    if (Context.showConfirmation("Have you confirmed all the information?"))
                        registerTeacher();
                });
            } else
                throw new NotImplementedError();
        });
    }

    public void registerStudent() {
        try {
            UserUtil.saveUser(new Student(studentId.getText(), studentName.getText(), studentPassword.getText(),
                    studentCollege.getText(), studentMajor.getText(), studentClassId.getText()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        registerButton.getStyleClass().add("success");
        registerButton.setText("Registered!");
        registerButton.setDisable(true);
    }

    public void registerTeacher() {
        try {
            UserUtil.saveUser(new Teacher(teacherId.getText(), teacherName.getText(), teacherPassword.getText(),
                    isAdmin.isSelected()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        registerButton.getStyleClass().add("success");
        registerButton.setText("Registered!");
        registerButton.setDisable(true);
    }
}
