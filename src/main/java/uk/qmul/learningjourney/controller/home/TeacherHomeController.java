package uk.qmul.learningjourney.controller.home;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;

import java.io.IOException;

public class TeacherHomeController extends BaseController {
    @Override
    public void initialize() {
    }

    @FXML
    public void onCourseSchedule(MouseEvent event) {
        try {
            Context.toNextScene("view/course/course-schedule-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void toRegisterPage(MouseEvent event) {
        try {
            Context.toNextScene("view/register-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
