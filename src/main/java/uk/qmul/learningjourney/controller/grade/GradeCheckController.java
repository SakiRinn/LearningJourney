package uk.qmul.learningjourney.controller.grade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.util.GradeUtil;
import uk.qmul.learningjourney.model.Grade;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GradeCheckController implements Initializable {

    @FXML
    private TableView<Grade> table;

    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Grade> gradeList = null;
        gradeList = FXCollections.observableArrayList(GradeUtil.getGrades());
        table.getItems().setAll(gradeList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("course"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("credit"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("GPA"));
    }

    @FXML
    public void toMainPage(MouseEvent event) {
        try {
            Context.toNextScene("view/grade/grade-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void export(MouseEvent event) {
        try {
            GradeUtil.generateWord(GradeUtil.getGrades(), "./test.docx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
