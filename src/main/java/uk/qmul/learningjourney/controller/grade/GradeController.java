package uk.qmul.learningjourney.controller.grade;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.controller.BaseController;
import uk.qmul.learningjourney.controller.course.CourseCreateController;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;

/**
 * The controller of the view file `grade-view.fxml`.
 *
 * @author Lyuhua Wu
 */
public class GradeController extends BaseController {

    @FXML
    private Label gpa;

    @Override
    public void initialize() {
        gpa.setText(Double.valueOf(GradeUtil.getWeightedGPA()).toString());
    }

    /**
     * Jump to the view `grade-list-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void toListPage(MouseEvent event) {
        try {
            Context.toNextScene("view/grade/grade-list-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Jump to the view `grade-analysis-view.fxml`.
     * <p>
     *     This function is bound to a button and is called when the button is clicked.
     * </p>
     *
     * @see CourseCreateController
     *
     * @param event Mouse click
     */
    @FXML
    public void toAnalysisPage(MouseEvent event) {
        try {
            Context.toNextScene("view/grade/grade-analysis-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}