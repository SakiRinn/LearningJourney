package uk.qmul.learningjourney.controller.user;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import kotlin.NotImplementedError;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.model.user.User;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;
import uk.qmul.learningjourney.util.UserUtil;

import java.io.IOException;
import java.util.ArrayList;

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

    @FXML
    public void toUserRegisterPage(MouseEvent event) {
        if (!((Teacher) Context.user).isAdmin()) {
            Context.showError("Only administrators have permission to use it!");
            return;
        }
        try {
            Context.toNextScene("view/user/user-register-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteUser() {
        if (!Context.showConfirmation("Are you sure to delete?"))
            return;
        User selected = choiceBox.getSelectionModel().getSelectedItem();
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
                    courses.removeIf(c -> c.equals(course));
                }
                DataIO.saveObjects(courses, Course.class);
            }

            UserUtil.saveUsers(users);
            Context.showInformation("Success to delete a user!");
            Context.toTeacherHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
