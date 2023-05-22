package uk.qmul.learningjourney;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import uk.qmul.learningjourney.grade.Grade;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PositionController implements Initializable {

    @FXML
    private StackPane pages;
    @FXML
    private AnchorPane acedamic;
    @FXML
    private AnchorPane extracuri;
    @FXML
    private TableView<Position> acTable;
    @FXML
    private TableView<Position> exTable;

    //private isEarlytoLatest =



    @FXML
    private void switchToContest(ActionEvent event) {
        for (Node page: pages.getChildren()) {
            page.setVisible(false);
        }
        acedamic.setVisible(true);
    }

    @FXML
    private void switchToPosition(ActionEvent event) {
        for (Node page: pages.getChildren()) {
            page.setVisible(false);
        }
        extracuri.setVisible(true);
    }

    @FXML
    private void sortPosition(ActionEvent event) {
        ArrayList<Position> allPos = Context.student.getPosition();
        ArrayList<Position> posNotCre = new ArrayList<>();
        ArrayList<Position> posCredit = new ArrayList<>();
        if (allPos != null) {
            for (Position tempPos : allPos) {
                if (tempPos.getIsCreditable() == true)
                    posCredit.add(tempPos);
                else
                    posNotCre.add(tempPos);
            }
        }
        if (posCredit!= null) {
            acTable.getItems().setAll(posCredit);
            acTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
            acTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
            acTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("isCreditable"));
        }
        if (posNotCre!= null) {
            exTable.getItems().setAll(posNotCre);
            exTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
            exTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
            exTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("isCreditable"));
        }

    }



    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Position> allPos = Context.student.getPosition();
        ArrayList<Position> posNotCre = new ArrayList<>();
        ArrayList<Position> posCredit = new ArrayList<>();

        if (allPos != null) {
            for (Position tempPos : allPos) {
                if (tempPos.getIsCreditable() == true)
                    posCredit.add(tempPos);
                else
                    posNotCre.add(tempPos);
            }
        }
        if (posCredit!= null) {
            acTable.getItems().setAll(posCredit);
            acTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
            acTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
            acTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("isCreditable"));
        }
        if (posNotCre!= null) {
            exTable.getItems().setAll(posNotCre);
            exTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
            exTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
            exTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("isCreditable"));
        }
    }

}



