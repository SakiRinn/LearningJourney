package uk.qmul.learningjourney.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Achievement;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.util.DataIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Controller of the Achievement page
 *
 * @author Chenxu Shi
 */
public class AchievementController extends BaseController {
    /**
     * export button
     */
    @FXML
    private Button export;
    /**
     * stackpane of the two pages
     */
    @FXML
    private StackPane pages;
    /**
     * anchor pane of the academic page
     */
    @FXML
    private AnchorPane academic;
    /**
     * anchor pane of the extracurri page
     */
    @FXML
    private AnchorPane extracuri;
    /**
     * table for creditable achievements
     */
    @FXML
    private TableView<Achievement> acTable;
    /**
     * table for extra achievements
     */
    @FXML
    private TableView<Achievement> exTable;
    /**
     * whether the achievement is currently sorted by chronological order
     */
    public Boolean isEarlytoLatest = false;

    /**
     * Switch to extra table
     */
    @FXML
    private void switchToContest(ActionEvent event) {
        for (Node page : pages.getChildren()) {
            page.setVisible(false);
        }
        academic.setVisible(true);
    }

    /**
     * Switch to academic table
     */
    @FXML
    private void switchToPosition(ActionEvent event) {
        for (Node page : pages.getChildren()) {
            page.setVisible(false);
        }
        extracuri.setVisible(true);
    }

    /**
     * Sort acievements in chronological order or reversed
     */
    @FXML
    private void sortPosition(ActionEvent event) {
        Student student = (Student) Context.user;
        ArrayList<Achievement> allPos = student.getPosition();
        ArrayList<Achievement> posNotCre = new ArrayList<>();
        ArrayList<Achievement> posCredit = new ArrayList<>();
        if (allPos != null) {
            for (Achievement tempPos : allPos) {
                if (tempPos.getIsCreditable() == true)
                    posCredit.add(tempPos);
                else
                    posNotCre.add(tempPos);
            }
        }
        if (isEarlytoLatest) {
            Collections.sort(posCredit, (p1, p2) -> p1.date.compareTo(p2.date));
            Collections.sort(posNotCre, (p1, p2) -> p1.date.compareTo(p2.date));
            isEarlytoLatest = false;
        } else{
            Collections.sort(posCredit, (p1, p2) -> p2.date.compareTo(p1.date));
            Collections.sort(posNotCre, (p1, p2) -> p2.date.compareTo(p1.date));
            isEarlytoLatest = true;
        }

        if (posCredit!= null) {
            acTable.getItems().setAll(posCredit);
            acTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
            acTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
            acTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("isCreditable"));
        }
        if (posNotCre != null) {
            exTable.getItems().setAll(posNotCre);
            exTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
            exTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
            exTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("isCreditable"));
        }
    }

    /**
     * export acievements to doc
     */
    @FXML
    public void exportPosition(ActionEvent event) throws IOException {
        Student student = (Student) Context.user;
        DataIO.exportAchievement(student.getPosition(), student.getName());
        export.getStyleClass().add("success");
        export.setText("Exported!");
        export.setDisable(true);
    }

    /**
     * Initialize the controller.
     */
    @Override
    public void initialize() {
        Student student = (Student) Context.user;
        ActionEvent e = null;
        sortPosition(e);
    }

}



