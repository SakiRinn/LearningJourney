package uk.qmul.learningjourney;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.ArrayList;

public class CourseSelectionController extends BaseController {
    @FXML
    private GridPane gridPane;
    private ArrayList<RowConstraints> rowConstraints;

    @Override
    public void initialize() {
        addRow();
    }

    public void addRow() {


        try {
            ArrayList<Course> courses = Util.getAvailCourses();
            for (Course course : courses) {
                Label id = new Label(course.getId());
                Label name = new Label(course.getName());
                Label credit = new Label(String.valueOf(course.getCredit()));
                Label classroom = new Label(course.getRoom());
                Button button = new Button("Select");
                button.setOnAction(actionEvent -> {
                    try {
                        Util.studentAddCourse(course);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    button.getStyleClass().add("success");
                    button.setText("Selected!");
                    button.setDisable(true);
                });
                int row = gridPane.getRowCount();
                gridPane.add(id, 0, row);
                gridPane.add(name, 1, row);
                gridPane.add(credit, 2, row);
                gridPane.add(classroom, 3, row);
                gridPane.add(button, 4, row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void addLabel() {

    }

    public void addButton(int row) {

        Button button = new Button("Select");
        gridPane.add(button, row, 4);
        button.setOnAction(actionEvent -> {
            button.getStyleClass().add("success");
            button.setText("Selected!");
            button.setDisable(true);
        });
    }

}
