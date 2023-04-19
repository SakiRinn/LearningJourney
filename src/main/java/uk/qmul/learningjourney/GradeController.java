package uk.qmul.learningjourney;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GradeController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label stuNum;
    @FXML
    private Label academy;
    @FXML
    private Label major;

    @FXML
    private Hyperlink home;
    @FXML
    private Hyperlink university;
    @FXML
    private Hyperlink blog;

    @FXML
    private Label gpa;

    @FXML
    private StackPane pages;
    @FXML
    private AnchorPane mainPage;
    @FXML
    private AnchorPane checkPage;

    public void initialize(URL location, ResourceBundle resources) {
        name.setText("Zerkai Niu");
        stuNum.setText("2020213156");
        academy.setText("International College");
        major.setText("Telecommunication & Management");

        gpa.setText(Double.valueOf(3.97).toString());
        toMainPage(new ActionEvent());
    }

    @FXML
    private void toMainPage(ActionEvent event) {
        for (Node page: pages.getChildren()) {
            page.setVisible(false);
        }
        mainPage.setVisible(true);
    }

    @FXML
    private void toCheckPage(ActionEvent event) {
        for (Node page: pages.getChildren()) {
            page.setVisible(false);
        }
        checkPage.setVisible(true);
    }
}