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

    @FXML
    public void toCreateCoursePage(MouseEvent event) {
        try {
            Context.toNextScene("view/course/course-create-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteCourse() {
        if (!Context.showConfirmation("Are you sure to delete?"))
            return;
        Course selected = choiceBox.getSelectionModel().getSelectedItem();
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
