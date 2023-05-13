package uk.qmul.learningjourney.grade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GradeCheckController {

    @FXML
    TableView<Grade> table;

    @FXML
    public void initialize() {
        ObservableList<Grade> gradeList = FXCollections.observableArrayList(GradeModel.getGrades());
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("credit"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
        table.setItems(gradeList);
    }
}
