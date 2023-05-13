package uk.qmul.learningjourney;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class ClassScheduleController extends BaseController {

    @FXML
    private ComboBox<String> comboBox;
    private final ArrayList<String> weeks = new ArrayList<>();
    @FXML
    private Button exportButton;
    private ArrayList<Label> labels = new ArrayList<>();


    @FXML
    GridPane gridPane;

    public GridPane getGridPane() {
        return gridPane;
    }

    @FXML
    public void initialize() {
        setComboBox();
        setExportButton();
        changeSchedule(0);
    }

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

    private void setWeeks() {
        for (int i = 1; i <= 18; i++) {
            weeks.add("Week " + i);
        }
    }

    public void setExportButton() {
        ImageView img = new ImageView(getClass().getResource("image/export_icon.png").toString());
        img.setFitHeight(18);
        img.setFitWidth(18);
        exportButton.setGraphic(img);
        exportButton.setText("Export");
    }

    public void changeSchedule(int index) {
        ArrayList<Course> courses = Context.student.getCourses();
        clearPane();
        for (Course course : courses) {
            boolean hasCourse = false;
            int[] weeks = course.getWeeks();
            if (weeks == null) {
                hasCourse = true;
            } else {
                for (int week : weeks) {
                    if (week == index + 1) {
                        hasCourse = true;
                        break;
                    }
                }
            }
            if (hasCourse) {
                int[] days = course.getDays();
                int start = course.getTimes()[0];
                int end = course.getTimes()[1];
                for (int day : days) {
                    for (int j = start; j <= end; j++) {
                        Label label = new Label();
                        String name = course.getName();
                        String room = course.getRoom();
                        label.setText(name + "\n" + room);
                        labels.add(label);
                        gridPane.add(label, day, j);
                    }
                }
            }


        }
    }

    public void clearPane() {
        gridPane.getChildren().removeAll(labels);
    }


}
