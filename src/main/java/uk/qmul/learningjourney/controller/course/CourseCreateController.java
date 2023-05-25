package uk.qmul.learningjourney.controller.course;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import uk.qmul.learningjourney.controller.BaseController;

import java.util.ArrayList;
import java.util.Map;

public class CourseCreateController extends BaseController {

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField credit;
    @FXML
    private TextField room;

    @FXML
    private ComboBox<Integer> weekBox;
    @FXML
    private TextField classNum;
    @FXML
    private TableView<Map.Entry<String, ArrayList<Integer>>> schedule;

    @Override
    public void initialize() {

    }

    @FXML
    public void addSchedule() {

    }

    @FXML
    public void createCourse() {

    }
}
