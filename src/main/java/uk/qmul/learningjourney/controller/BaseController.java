package uk.qmul.learningjourney.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.MainApplication;

public class BaseController {

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

    public void initialize() {
        setNameLabel(Context.account.getName());
        setIdLabel(Context.account.getId());
        setCollegeLabel(Context.account.getCollege());
        setMajorLabel(Context.account.getMajor());
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
            Context.stage.setScene(Context.homeScene);
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
