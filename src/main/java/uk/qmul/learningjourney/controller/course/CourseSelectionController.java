package uk.qmul.learningjourney.controller.course;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.util.CourseUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller of course-selection-view
 *
 * @author Zekai Liu
 */
public class CourseSelectionController extends BaseController {

    /**
     * {@code GridPane} for displaying course selection
     */
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize() {
        addCourseItems();
    }

    /**
     * Add course items to gridPane
     */
    public void addCourseItems() {
        try {
            ArrayList<Course> courses = CourseUtil.getAvailCourses();
            for (Course course : courses) {
                int row = gridPane.getRowCount();
                addLabels(course, row);
                addButton(row, null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add course information
     * @param course course
     * @param row row index
     */

    public void addLabels(Course course, int row) {
        Label id = new Label(course.getId());
        Label name = new Label(course.getName());
        Label credit = new Label(String.valueOf(course.getCredit()));
        Label classroom = new Label(course.getRoom());
        gridPane.add(id, 0, row);
        gridPane.add(name, 1, row);
        gridPane.add(credit, 2, row);
        gridPane.add(classroom, 3, row);
    }

    /**
     * Add button for selection
     * @param row row index
     * @param course course
     */
    public void addButton(int row, Course course) {
        Button button = new Button("Select");
        gridPane.add(button, 4, row);
        button.setOnAction(actionEvent -> {
            try {
                CourseUtil.studentAddCourse(course);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            button.getStyleClass().add("success");
            button.setText("Selected!");
            button.setDisable(true);
        });
    }
}
