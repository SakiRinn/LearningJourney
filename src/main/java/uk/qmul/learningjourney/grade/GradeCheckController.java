package uk.qmul.learningjourney.grade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.DataIO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GradeCheckController implements Initializable {

    @FXML
    private TableView<Grade> table;

    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Grade> gradeList = null;
        try {
            gradeList = FXCollections.observableArrayList((ArrayList<Grade>) DataIO.loadObjects(Grade.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table.getItems().setAll(gradeList);
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("credit"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    @FXML
    public void toMainPage(MouseEvent event) {
        try {
            Context.toNextScene("grade-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void export(MouseEvent event) {
        try {
            GradeModel.generateWord(GradeModel.getGrades(), "./test.docx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
