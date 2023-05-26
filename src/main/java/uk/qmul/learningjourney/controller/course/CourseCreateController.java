package uk.qmul.learningjourney.controller.course;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.util.DataIO;

import java.io.IOException;
import java.util.*;

public class CourseCreateController extends BaseController {

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField credit;
    @FXML
    private TextField room;
    @FXML
    private ComboBox<Integer> semesterBox;

    @FXML
    private ComboBox<Integer> weekBox;
    @FXML
    private TextField classNum;
    @FXML
    private TableView<Map.Entry<Integer, Integer[]>> schedule;

    HashMap<Integer, Integer[]> scheduleMap = new HashMap<>();

    @Override
    public void initialize() {

    }

    @FXML
    public void addSchedule() {
        int week = weekBox.getSelectionModel().getSelectedItem();
        String[] classStrings = classNum.getText().split(", ");
        Integer[] classes = new Integer[classStrings.length];
        try {
            for (int i = 0; i < classStrings.length; i++)
                classes[i] = Integer.parseInt(classStrings[i]);
        } catch (NumberFormatException e) {
            Context.showError("Invalid format! Please check and enter again.");
            return;
        }
        scheduleMap.put(week, classes);
    }

    @FXML
    public void createCourse() {
        try {
            ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
            courses.add(new Course(name.getText(), id.getText(), Context.user.getName(), 0,
                    Double.parseDouble(credit.getText()), room.getText(), 0, scheduleMap));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Context.showInformation("Success to register a new course!");
        try {
            Context.toTeacherHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void renewTable() {
        ObservableList<Map.Entry<Integer, Integer[]>> data = FXCollections.observableArrayList();
        data.addAll(scheduleMap.entrySet());
        schedule.getColumns().get(0).setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));
        schedule.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}
