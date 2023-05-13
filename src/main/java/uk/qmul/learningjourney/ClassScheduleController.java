package uk.qmul.learningjourney;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class ClassScheduleController extends BaseController {

    @FXML
    private ComboBox<String> comboBox;
    private final ArrayList<String> weeks = new ArrayList<>();
    @FXML
    private Button exportButton;


    @FXML
    GridPane gridPane;

    public GridPane getGridPane() {
        return gridPane;
    }

    @FXML
    public void initialize() {
        setComboBox();
        setExportButton();
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

    public void setExportButton() {
        ImageView img = new ImageView(getClass().getResource("image/export_icon.png").toString());
        img.setFitHeight(18);
        img.setFitWidth(18);
        exportButton.setGraphic(img);
        exportButton.setText("Export");
    }

    public void changeSchedule(int index) {

    }


}
