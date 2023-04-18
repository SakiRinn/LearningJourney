package uk.qmul.learningjourney;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static uk.qmul.learningjourney.ClassSchedule.CLASSCOUNT;

public class ClassScheduleView {
    List<String> classTime = new ArrayList<>();
    TableView tableView = new TableView<>();

    @FXML
    ComboBox<String> comboBox;
    private ArrayList<String> weeks = new ArrayList<>();
    TableColumn column0 = new TableColumn("Time");
    TableColumn column1 = new TableColumn("Monday");
    TableColumn column2 = new TableColumn("Tuesday");
    TableColumn column3 = new TableColumn("Wednesday");
    TableColumn column4 = new TableColumn("Thursday");
    TableColumn column5 = new TableColumn("Friday");
    TableColumn column6 = new TableColumn("Saturday");
    TableColumn column7 = new TableColumn("Sunday");

    public void createSchedule() {
        SimpleDateFormat sdf = new SimpleDateFormat("hhmm");

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse("0800"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < CLASSCOUNT; i++) {
            StringBuilder builder = new StringBuilder(i + "\n");
            builder.append(sdf.format(calendar.getTime()));
            builder.append("\n");
            calendar.add(Calendar.MINUTE, 45);
            builder.append(sdf.format(calendar.getTime()));
            String time = builder.toString();
            classTime.add(time);
        }

    }


    @FXML
    GridPane gridPane;

    public GridPane getGridPane() {
        return gridPane;
    }


    public void setComboBox() {
        setWeeks();
        ObservableList<String> items = FXCollections.observableArrayList(weeks);
        comboBox.getItems().addAll(items);
        comboBox.setOnAction(e -> changeSchedule(items.indexOf(comboBox.getValue())));

    }

    private void setWeeks() {
        for (int i = 1; i <= 18; i++) {
            weeks.add("Week " + i);
        }
    }

    public void changeSchedule(int index) {

    }


}
