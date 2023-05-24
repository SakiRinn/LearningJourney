package uk.qmul.learningjourney.controller;

import javafx.fxml.FXML;
import uk.qmul.learningjourney.Context;

import java.io.IOException;

public class StudentHomeController extends BaseController {

    @Override
    public void initialize() {
    }

    @FXML
    public void onGrade() {
        try {
            Context.toNextScene("view/grade/grade-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void onAchievement() {
        try {
            Context.toNextScene("view/position-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void onCourseSchedule() {
        try {
            Context.toNextScene("view/course/course-schedule-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void onCourseSelection() {
        try {
            Context.toNextScene("view/course/course-selection-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
