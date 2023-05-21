package uk.qmul.learningjourney.grade;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import uk.qmul.learningjourney.DataIO;
import uk.qmul.learningjourney.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GradeAnalysisController implements Initializable {

    @FXML
    Label text;

    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Student> students = null;
        try {
            students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        text.setText("Rank: " + GradeModel.getRank() + '\n'
                + "Rank Percentage: " + Double.valueOf((double) GradeModel.getRank() / students.size() * 100).toString().substring(0, 4) + '%')
        ;
    }


}
