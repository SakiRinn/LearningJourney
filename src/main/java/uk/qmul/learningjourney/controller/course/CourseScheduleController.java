package uk.qmul.learningjourney.controller.course;

import fr.opensagres.xdocreport.core.XDocReportException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.util.CourseUtil;
import uk.qmul.learningjourney.util.DataIO;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Controller of course-schedule-view
 *
 * @author Zekai Liu
 */
public class CourseScheduleController extends BaseController {

    /**
     * A ComboBox for selecting week
     */
    @FXML
    private ComboBox<String> comboBox;
    /**
     * Export button
     */
    @FXML
    private Button exportButton;
    /**
     * The GridPane showing schedule
     */
    @FXML
    private GridPane gridPane;

    /**
     * Indicate the current week in schedule
     */
    private int currentWeek;
    /**
     *
     */
    private final ArrayList<String> weeks = new ArrayList<>();
    /**
     * Labels to show course information
     */
    private ArrayList<Label> labels = new ArrayList<>();


    @Override
    public void initialize() {
        setComboBox();
        changeSchedule(0);
    }

    /**
     * set the ComboBox
     */
    @FXML
    public void setComboBox() {
        setWeeks();
        ObservableList<String> items = FXCollections.observableArrayList(weeks);
        comboBox.getItems().addAll(items);
        comboBox.setValue(weeks.get(0));
        comboBox.setOnAction(e -> {
            changeSchedule(items.indexOf(comboBox.getValue()));
        });
    }

    /**
     * Set the text for comboBox
     */
    private void setWeeks() {
        for (int i = 1; i <= 18; i++) {
            weeks.add("Week " + i);
        }
    }

    /**
     * Set the export button
     */
    @FXML
    public void onExport() {

        Student student = (Student) Context.user;
        try {
            DataIO.exportSchedule(student.getName(), currentWeek, student.getCourses());
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
        exportButton.getStyleClass().add("success");
        exportButton.setText("Exported!");
        exportButton.setDisable(true);
    }

    /**
     * Change schedule to a given week
     *
     * @param index week - 1
     */
    public void changeSchedule(int index) {
        ArrayList<String> courses = new ArrayList<>();
        if (Context.user instanceof Student)
            courses = ((Student) Context.user).getCourses();
        else if (Context.user instanceof Teacher)
            courses = ((Teacher) Context.user).getCourses();
        clearPane();
        currentWeek = index + 1;
        for (String id : courses) {
            Course course;
            try {
                course = CourseUtil.getCourse(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int week = 0;
            if (course.getSchedule().containsKey(index + 1)) {
                week = index + 1;
            } else if (!course.getSchedule().containsKey(0)) {
                continue;
            }
            String name = course.getName();
            String room = course.getRoom();
            String str = name + "\n" + room;
            for (int i : course.getSchedule().get(week)) {
                int day = i / 14 + 1;
                int num = i % 14;
                Label label = new Label();
                label.setText(str);
                labels.add(label);
                gridPane.add(label, day, num);
            }
        }
    }

    /**
     * Clear all added labels in the gridPane
     */
    public void clearPane() {
        gridPane.getChildren().removeAll(labels);
    }
}
