package uk.qmul.learningjourney.controller.grade;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;
import java.util.ArrayList;

public class GradeAnalysisController extends BaseController {

    @FXML
    Label text;

    public void initialize() {
        ArrayList<Student> students = null;
        try {
            students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        text.setText("Rank: " + GradeUtil.getRank() + '\n'
                + "Rank Percentage: " + Double.valueOf((double) GradeUtil.getRank() / students.size() * 100).toString().substring(0, 4) + '%')
        ;
    }


}
