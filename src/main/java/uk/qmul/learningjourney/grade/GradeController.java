package uk.qmul.learningjourney.grade;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GradeController implements Initializable {

    @FXML
    private Label gpa;

    public void initialize(URL location, ResourceBundle resources) {
        gpa.setText(Double.valueOf(GradeModel.getAverageGPA()).toString());
    }

    @FXML
    public void toCheckPage(MouseEvent event) {
        try {
            Context.toNextScene("grade-check-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void toAnalysisPage(MouseEvent event) {
        try {
            Context.toNextScene("grade-analysis-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}