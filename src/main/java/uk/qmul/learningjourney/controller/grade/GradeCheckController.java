package uk.qmul.learningjourney.controller.grade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The controller of the view file `grade-check-view.fxml`.
 *
 * @author Lyuhua Wu
 */
public class GradeCheckController extends BaseController {

    @FXML
    private TableView<Grade> table;
    @FXML
    private ComboBox<String> courseBox;

    @Override
    public void initialize() {
        for (Course course : ((Teacher) Context.user).getCourseList())
            courseBox.getItems().add(course.getName());
        courseBox.getItems().add("All");
        courseBox.setValue("All");
        courseBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                for (Course course : (ArrayList<Course>) DataIO.loadObjects(Course.class)) {
                    if (course.getName().equals(newValue)) {
                        showCourseGrades(course);
                        return;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (newValue.equals("All"))
                showCourseGrades();
        });
        showCourseGrades();
    }

    /**
     * Display all grades for all courses taught by the current teacher in a table.
     */
    public void showCourseGrades() {
        ArrayList<Grade> grades = new ArrayList<>();
        for (Course course : ((Teacher) Context.user).getCourseList())
            grades.addAll(GradeUtil.getCourseGrades(course));
        ObservableList<Grade> gradeList = FXCollections.observableArrayList(grades);
        table.getItems().setAll(gradeList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseName"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("student"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("GPA"));
    }

    /**
     * Display all grades for a specific course in a table.
     */
    public void showCourseGrades(Course course) {
        ObservableList<Grade> gradeList = FXCollections.observableArrayList(GradeUtil.getCourseGrades(course));
        table.getItems().setAll(gradeList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseName"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("student"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("GPA"));
    }
}
