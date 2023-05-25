package uk.qmul.learningjourney.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.MainApplication;
import uk.qmul.learningjourney.model.user.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {

    @FXML
    private Button homeButton;
    @FXML
    private Button backButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label collegeLabel;
    @FXML
    private Label majorLabel;

    public BaseController() {
        Context.controllers.put(this.getClass().getSimpleName(), this);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNameLabel(Context.user.getName());
        setIdLabel(Context.user.getId());
        if (Context.user instanceof Student) {
            Student student = (Student) Context.user;
            setCollegeLabel(student.getCollege());
            setMajorLabel(student.getMajor());
        }
        setHomeButton();
        setBackButton();
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    public void setIdLabel(String id) {
        idLabel.setText(id);
    }

    public void setCollegeLabel(String college) {
        collegeLabel.setText(college);
    }

    public void setMajorLabel(String major) {
        majorLabel.setText(major);
    }

    public void setHomeButton() {
        ImageView img = new ImageView(MainApplication.class.getResource("image/home-icon.png").toString());
        img.setFitHeight(20);
        img.setFitWidth(20);
        homeButton.setGraphic(img);
        homeButton.setOnAction(actionEvent -> {
            Context.toHome();
        });
    }

    public void setBackButton() {
        ImageView img = new ImageView(MainApplication.class.getResource("image/back-icon.png").toString());
        img.setFitHeight(20);
        img.setFitWidth(20);
        backButton.setGraphic(img);
        backButton.setOnAction(actionEvent -> Context.toLastScene());
    }
}
