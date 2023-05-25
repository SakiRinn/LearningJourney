package uk.qmul.learningjourney.controller.grade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.util.GradeUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class GradeCheckController implements Initializable {

    @FXML
    private TableView<Grade> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ObservableList<Grade> gradeList = null;
//        gradeList = FXCollections.observableArrayList(GradeUtil.getCourseGrades(course));
//        table.getItems().setAll(gradeList);
//        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseName"));
//        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("credit"));
//        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
//        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("GPA"));
    }
}
