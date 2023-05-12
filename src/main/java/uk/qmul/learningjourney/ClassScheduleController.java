package uk.qmul.learningjourney;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


public class ClassScheduleController extends BaseController {


    List<String> classTime = new ArrayList<>();
    TableView tableView = new TableView<>();

    @FXML
    ComboBox<String> comboBox;
    private ArrayList<String> weeks = new ArrayList<>();


    @FXML
    GridPane gridPane;

    public GridPane getGridPane() {
        return gridPane;
    }

    @FXML
    public void initialize() {
        setComboBox();
    }

    @FXML
    public void setComboBox() {
        setWeeks();
        ObservableList<String> items = FXCollections.observableArrayList(weeks);
        comboBox.getItems().addAll(items);
        comboBox.setValue(weeks.get(0));
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
