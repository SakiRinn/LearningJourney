package uk.qmul.learningjourney.controller.home;

import javafx.fxml.FXML;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;

import java.io.IOException;

public class TeacherHomeController extends BaseController {
    @Override
    public void initialize() {
    }

    @FXML
    public void onCourseSchedule() {
        try {
            Context.toNextScene("view/course/course-schedule-view.fxml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
