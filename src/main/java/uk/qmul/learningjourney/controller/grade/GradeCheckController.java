package uk.qmul.learningjourney.controller.grade;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.Grade;

public class GradeCheckController extends BaseController {

    @FXML
    private TableView<Grade> table;

    @Override
    public void initialize() {
//        ObservableList<Grade> gradeList = null;
//        gradeList = FXCollections.observableArrayList(GradeUtil.getCourseGrades(course));
//        table.getItems().setAll(gradeList);
//        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseName"));
//        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("credit"));
//        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
//        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("GPA"));
    }
}
