package uk.qmul.learningjourney.controller.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.controller.course.CourseCreateController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.model.user.User;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.UserUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The controller of the view file `user-management-view.fxml`.
 * <p>
 *     The administrator manages all the users in this page.
 *     It provides the following functions:
 *     <ol>
 *         <li>Show all users</li>
 *         <li>Add new users</li>
 *         <li>Delete users</li>
 *     </ol>
 * </p><p>
 *     Note that Only administrators have permission to access this page and use the functions.
 * </p>
 *
 * @author Lyuhua Wu
 * @see BaseController
 */
public class UserManagementController extends BaseController {

    @FXML
    private TableView<User> table;
    @FXML
    private ComboBox<User> choiceBox;

    public void initialize() {
        ArrayList<User> users = null;
        try {
            users =  UserUtil.loadUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObservableList<User> userList = FXCollections.observableArrayList(users);
        table.getItems().addAll(userList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("type"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("password"));

        users.removeIf(user -> user.getId().equals(Context.user.getId()));
        userList = FXCollections.observableArrayList(users);
        choiceBox.getItems().addAll(userList);
    }

    /**
     * Jump to the view `user-register-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void toUserRegisterPage(MouseEvent event) {
        try {
            Context.toNextScene("view/user/user-register-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a course.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p><p>
     *     Read the selected user in the choise box and delete it.
     * </p><p>
     *     If the deleted user is a teacher, all the courses taught by the user will be automatically removed.
     *     If a student, all his / her grades will be removed automatically.
     * </p>
     */
    @FXML
    public void deleteUser() {
        if (!Context.showConfirmation("Are you sure to delete?"))
            return;
        User selected = choiceBox.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Context.showError("You must choose a user to delete!");
            return;
        }
        try {
            ArrayList<String> teachingCourses = (selected instanceof Teacher) ? ((Teacher) selected).getCourses() : null;
            ArrayList<User> users = UserUtil.loadUsers();
            users.removeIf(user -> user.getId().equals(selected.getId()));

            if (teachingCourses != null) {
                ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
                for (String course : teachingCourses) {
                    for (User user : users) {
                        if (user instanceof Student) {
                            ArrayList<String> stuCourses = ((Student) user).getCourses();
                            stuCourses.removeIf(c -> c.equals(course));
                            ((Student) user).setCourses(stuCourses);
                        }
                    }
                    courses.removeIf(c -> c.getId().equals(course));
                }
                DataIO.saveObjects(courses, Course.class);
            } else {
                ArrayList<Grade> grades = (ArrayList<Grade>) DataIO.loadObjects(Grade.class);
                grades.removeIf(g -> g.getStudent().equals(selected.getName()));
                DataIO.saveObjects(grades, Grade.class);
            }
            UserUtil.saveUsers(users);
            Context.showInformation("Success to delete a user!");
            Context.toTeacherHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
