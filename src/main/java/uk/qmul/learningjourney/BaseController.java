package uk.qmul.learningjourney;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BaseController {

    @FXML
    private Button homeButton;
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
        setNameLabel("Wu lyuhua");
        setIdLabel("2020213171");
        setCollegeLabel("International School");
        setMajorLabel("Telecommunication and Management");

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
}
