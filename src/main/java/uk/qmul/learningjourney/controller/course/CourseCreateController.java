package uk.qmul.learningjourney.controller.course;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.model.user.User;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.UserUtil;

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

    private final HashMap<Integer, Integer[]> scheduleMap = new HashMap<>();
    @FXML
    private TableView<Map.Entry<Integer, Integer[]>> scheduleTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer[]>, String> week;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer[]>, String> numbers;

    @Override
    public void initialize() {
        for (int i = 0; i <= 18; i++)
            weekBox.getItems().add(i);
        weekBox.setValue(0);
        for (int i = 1; i <= 8; i++)
            semesterBox.getItems().add(i);
        semesterBox.setValue(1);
        refreshScheduleTable();

        TextFormatter<String> creditFormatter = new TextFormatter<>(text -> {
            String newText = text.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return text;
            }
            return null;
        });
        credit.setTextFormatter(creditFormatter);
    }

    @FXML
    public void addSchedule() {
        int week = weekBox.getSelectionModel().getSelectedItem();
        String[] classStrings = classNum.getText().split(", ");
        Integer[] classes = new Integer[classStrings.length];
        try {
            for (int i = 0; i < classStrings.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (classStrings[i].equals(classStrings[j])) {
                        Context.showError("No repetition allowed!");
                        return;
                    }
                }
                classes[i] = Integer.parseInt(classStrings[i]);
            }
        } catch (NumberFormatException e) {
            Context.showError("Invalid format! Please check and enter again.");
            return;
        }
        scheduleMap.put(week, classes);
        refreshScheduleTable();
    }

    @FXML
    public void createCourse() {
        if (!Context.showConfirmation("Have you confirmed all the information?"))
            return;
        try {
            ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
            Course newCourse = new Course(name.getText(), id.getText(), Context.user.getName(), 0,
                    Double.parseDouble(credit.getText()), room.getText(),
                    semesterBox.getSelectionModel().getSelectedItem(), scheduleMap);
            courses.add(newCourse);

            DataIO.saveObjects(courses, Course.class);
            ArrayList<String> cs = ((Teacher) Context.user).getCourses();
            cs.add(newCourse.getId());
            ArrayList<User> users = UserUtil.loadUsers();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if ((user instanceof Teacher) && user.getId().equals(Context.user.getId())) {
                    ((Teacher) user).setCourses(cs);
                    users.set(i, user);
                }
            }
            UserUtil.saveUsers(users);
            ((Teacher) Context.user).setCourses(cs);

            Context.showInformation("Success to create a new course!");
            Context.toTeacherHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshScheduleTable() {
        week.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getKey())));
        numbers.setCellValueFactory(param -> new SimpleStringProperty(array2String(param.getValue().getValue())));
        ObservableList<Map.Entry<Integer, Integer[]>> data = FXCollections.observableArrayList();
        data.setAll(scheduleMap.entrySet());
        scheduleTable.getItems().setAll(data);
    }

    private String array2String(Integer[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
