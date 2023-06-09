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

/**
 * The controller of the view file `grade-list-view.fxml`.
 *
 * @author Lyuhua Wu
 */
public class GradeListController extends BaseController {

    @FXML
    private Button exportButton;
    @FXML
    private TableView<Grade> table;

    @Override
    public void initialize() {
        ObservableList<Grade> gradeList = null;
        gradeList = FXCollections.observableArrayList(GradeUtil.getGrades());
        table.getItems().setAll(gradeList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseName"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("credit"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("GPA"));
    }

    /**
     * Exporting transcripts.
     *
     * @param event Mouse click
     */
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
