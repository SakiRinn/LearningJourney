package uk.qmul.learningjourney.controller.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.model.user.User;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.UserUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The controller of the view file `course-management-view.fxml`.
 * <p>
 *     The user manages all the courses in this page.
 *     It provides the following functions:
 *     <ol>
 *         <li>Show all courses</li>
 *         <li>Add new courses</li>
 *         <li>Delete courses</li>
 *     </ol>
 * </p><p>
 *     All teachers have permission to enter this view and use it.
 *     There is a table showing all the courses taught by the current teacher.
 * </p>
 *
 * @author Lyuhua Wu
 * @see BaseController
 */
public class CourseManagementController extends BaseController {

    @FXML
    private TableView<Course> table;
    @FXML
    private ComboBox<Course> choiceBox;

    public void initialize() {
        ArrayList<Course> courses = ((Teacher) Context.user).getCourseList();
        ObservableList<Course> courseList = FXCollections.observableArrayList(courses);
        choiceBox.getItems().addAll(courseList);
        table.getItems().addAll(courseList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("teacher"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("room"));
    }

    /**
     * Jump to the view `course-create-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void toCreateCoursePage(MouseEvent event) {
        try {
            Context.toNextScene("view/course/course-create-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a course.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p><p>
     *     Read the selected course in the choise box and delete it.
     *     The deleted course will be removed from all students' course selection lists
     *     as well as from the teacher's teaching list.
     * </p>
     */
    @FXML
    public void deleteCourse() {
        if (!Context.showConfirmation("Are you sure to delete?"))
            return;
        Course selected = choiceBox.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Context.showError("You must choose a course to delete!");
            return;
        }
        try {
            ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
            courses.removeIf(c -> c.getId().equals(selected.getId()));
            DataIO.saveObjects(courses, Course.class);
            ArrayList<User> users = UserUtil.loadUsers();
            for (User user : users) {
                if (user instanceof Student)
                    ((Student) user).getCourses().removeIf(c -> c.equals(selected.getId()));
                else if (user instanceof Teacher && user.getId().equals(Context.user.getId())) {
                    ((Teacher) user).getCourses().removeIf(c -> c.equals(selected.getId()));
                    ((Teacher) Context.user).getCourses().removeIf(c -> c.equals(selected.getId()));
                }
            }
            UserUtil.saveUsers(users);
            Context.showInformation("Success to delete a course!");
            Context.toTeacherHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
