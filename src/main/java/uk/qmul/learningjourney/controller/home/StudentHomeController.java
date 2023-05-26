package uk.qmul.learningjourney.controller.home;

import javafx.fxml.FXML;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;

import java.io.IOException;

/**
 * Controller for student's home view
 *
 * @author Zekai Liu
 * @date 2023/05/25
 */
public class StudentHomeController extends BaseController {

    @Override
    public void initialize() {
    }

    /**
     * Event handler for grade button
     */
    @FXML
    public void onGrade() {
        try {
            Context.toNextScene("view/grade/grade-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Event handler for achievement button
     */
    @FXML
    public void onAchievement() {
        try {
            Context.toNextScene("view/achievement-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Event handler for course button
     */
    @FXML
    public void onCourseSchedule() {
        try {
            Context.toNextScene("view/course/course-schedule-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Event handler for course selection button
     */
    @FXML
    public void onCourseSelection() {
        try {
            Context.toNextScene("view/course/course-selection-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
