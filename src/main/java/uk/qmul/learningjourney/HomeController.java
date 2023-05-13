package uk.qmul.learningjourney;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController extends BaseController {
    @FXML
    private Button gradeButton;
    @FXML
    private Button achievementButton;
    @FXML
    private Button courseScheduleButton;
    @FXML
    private Button courseSelectionButton;

    @Override
    public void initialize() {
        setGradeButton();
        setAchievementButton();
        setCourseScheduleButton();
        setCourseSelectionButton();
    }

    public void setGradeButton() {
        gradeButton.setOnAction(e -> {
            try {
                Context.toNextScene("grade-view.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setAchievementButton() {
        achievementButton.setOnAction(e -> {
            try {
                Context.toNextScene("grade-view.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setCourseScheduleButton() {
        courseScheduleButton.setOnAction(e -> {
            try {
                Context.toNextScene("schedule-view.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setCourseSelectionButton() {
        courseSelectionButton.setOnAction(e -> {
            try {
                Context.toNextScene("course-selection-view.fxml.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}