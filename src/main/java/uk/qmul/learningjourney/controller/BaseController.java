package uk.qmul.learningjourney.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.user.Student;

import java.io.IOException;

/**
 * Base view controller for all controllers.
 * Other controllers should inherit this class
 *
 * @author Zekai Liu
 * @date 2023/04/25
 */
public class BaseController {
    /**
     * Name label
     */
    @FXML
    private Label nameLabel;
    /**
     * ID label
     */
    @FXML
    private Label idLabel;
    /**
     * College label
     */
    @FXML
    private Label collegeLabel;
    /**
     * Major label
     */
    @FXML
    private Label majorLabel;


    /**
     * Initialize the controller.
     * <p>
     * All subclasses must override this method.
     */
    public void initialize() {
        setNameLabel(Context.user.getName());
        setIdLabel(Context.user.getId());
        if (Context.user instanceof Student) {
            Student student = (Student) Context.user;
            setCollegeLabel(student.getCollege());
            setMajorLabel(student.getMajor());
        }
    }

    /**
     * Set name label
     *
     * @param name name
     */
    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    /**
     * Set ID label
     *
     * @param id id
     */
    public void setIdLabel(String id) {
        idLabel.setText(id);
    }

    /**
     * Set college label
     *
     * @param college college
     */
    public void setCollegeLabel(String college) {
        collegeLabel.setText(college);
    }

    /**
     * Set major label
     *
     * @param major major
     */
    public void setMajorLabel(String major) {
        majorLabel.setText(major);
    }

    /**
     * Set home button
     */
    @FXML
    public void onHome() {
        Context.toHome();
    }

    /**
     * Set back button
     */
    @FXML
    public void onBack() {
        Context.toLastScene();
    }

    @FXML
    public void onLogout() throws IOException {
        Context.logout();
    }
}
