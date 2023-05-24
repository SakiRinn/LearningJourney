package uk.qmul.learningjourney.controller.grade;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.User;
import uk.qmul.learningjourney.util.GradeUtil;
import uk.qmul.learningjourney.util.UserUtil;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class GradeAnalysisController extends BaseController {

    @FXML
    Label numCourse;
    @FXML
    Label totalCredit;
    @FXML
    Label GPA;
    @FXML
    Label score;
    @FXML
    Label ranking;
    @FXML
    Label percentage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int numStudent = 0;
        try {
            for (User user : UserUtil.loadUsers()) {
                if (user instanceof Student)
                    numStudent++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        numCourse.setText(String.valueOf(((Student) Context.user).getCourses().size()));
        totalCredit.setText(String.valueOf(GradeUtil.getTotalCredit()));
        GPA.setText(String.valueOf(GradeUtil.getWeightedGPA()));
        score.setText(String.valueOf(GradeUtil.getWeightedScore()));
        ranking.setText(GradeUtil.getRank() + " / " + numStudent);
        percentage.setText(new DecimalFormat("#.##").format((double) GradeUtil.getRank() / numStudent * 100) + "%");
    }


}
