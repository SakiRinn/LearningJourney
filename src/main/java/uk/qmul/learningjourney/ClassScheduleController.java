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
    private int currentWeek;


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
        exportButton.setOnAction(event -> {
            Util.getInstance().exportSchedule(Context.student.getName(), currentWeek, Context.student.getCourses());
        });
    }

    public void changeSchedule(int index) {
        ArrayList<String> courses = Context.student.getCourses();
        clearPane();
        currentWeek = index + 1;
        for (String id : courses) {
            Course course = Util.getCourse(id);
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

    public void clearPane() {
        gridPane.getChildren().removeAll(labels);
    }


}
