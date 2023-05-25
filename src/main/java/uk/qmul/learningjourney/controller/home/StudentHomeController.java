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
     * Go to the grade view
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
     * Go to the achievement view
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
     * Go to the course schedule view
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
     * Go to the course selection view
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
