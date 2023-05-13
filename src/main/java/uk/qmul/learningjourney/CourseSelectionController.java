package uk.qmul.learningjourney;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

public class CourseSelectionController extends BaseController {
    @FXML
    private GridPane gridPane;
    private ArrayList<RowConstraints> rowConstraints;

    public void addRow() {
        RowConstraints row = new RowConstraints();
        gridPane.addRow(gridPane.getRowCount());

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
