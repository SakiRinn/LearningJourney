package uk.qmul.learningjourney.controller.home;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.controller.course.CourseCreateController;
import uk.qmul.learningjourney.model.user.Teacher;

import java.io.IOException;

/**
 * The controller of the view file `teacher-home-view.fxml`.
 * <p>
 *     Main menu for teachers, including the following functions:
 *     <ol>
 *         <li>Check the teaching schedule.</li>
 *         <li>Check students' grades.</li>
 *         <li>Manage all users. (Administrator only)</li>
 *         <li>Manage the teaching courses.</li>
 *     </ol>
 * </p>
 *
 * @author Lyuhua Wu
 * @see BaseController
 */
public class TeacherHomeController extends BaseController {
    @Override
    public void initialize() {
    }

    /**
     * Jump to the view `course-schedule-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void onCourseSchedule(MouseEvent event) {
        try {
            Context.toNextScene("view/course/course-schedule-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Jump to the view `grade-check-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void toGradeCheckPage(MouseEvent event) {
        try {
            Context.toNextScene("view/grade/grade-check-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Jump to the view `user-management-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void toUserManagementPage(MouseEvent event) {
        if (!((Teacher) Context.user).isAdmin()) {
            Context.showError("Only administrators have permission to use it!");
            return;
        }
        try {
            Context.toNextScene("view/user/user-management-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Jump to the view `course-management-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void toCourseManagementPage(MouseEvent event) {
        try {
            Context.toNextScene("view/course/course-management-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
