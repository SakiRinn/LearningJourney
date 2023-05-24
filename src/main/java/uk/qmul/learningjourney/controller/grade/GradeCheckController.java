package uk.qmul.learningjourney.controller.grade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GradeCheckController extends BaseController {

    public Button exportButton;
    @FXML
    private TableView<Grade> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Grade> gradeList = null;
        gradeList = FXCollections.observableArrayList(GradeUtil.getGrades());
        table.getItems().setAll(gradeList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("course"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("credit"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("GPA"));
    }

    @FXML
    public void export(MouseEvent event) {
        try {
            DataIO.exportGrade(GradeUtil.getGrades(), Context.user.getName());
            exportButton.getStyleClass().add("success");
            exportButton.setText("Exported!");
            exportButton.setDisable(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
