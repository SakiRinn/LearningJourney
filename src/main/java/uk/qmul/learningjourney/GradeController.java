package uk.qmul.learningjourney;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GradeController implements Initializable {



    @FXML
    private Label gpa;

    @FXML
    private StackPane pages;
    @FXML
    private AnchorPane mainPage;
    @FXML
    private AnchorPane checkPage;

    public void initialize(URL location, ResourceBundle resources) {

        gpa.setText(Double.valueOf(3.97).toString());

    }


}