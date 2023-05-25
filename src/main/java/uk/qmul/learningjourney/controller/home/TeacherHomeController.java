package uk.qmul.learningjourney.controller.home;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.user.Teacher;

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
    public void toGradeCheckPage(MouseEvent event) {
        try {
            Context.toNextScene("view/grade/grade-check-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void toUserManagementPage(MouseEvent event) {
        try {
            Context.toNextScene("view/user/user-management-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
