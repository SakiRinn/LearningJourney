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
import uk.qmul.learningjourney.model.Position;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.util.DataIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class PositionController extends BaseController {

    public Button export;
    @FXML
    private StackPane pages;
    @FXML
    private AnchorPane academic;
    @FXML
    private AnchorPane extracuri;
    @FXML
    private TableView<Position> acTable;
    @FXML
    private TableView<Position> exTable;

    public Boolean isEarlytoLatest = false;


    @FXML
    private void switchToContest(ActionEvent event) {
        for (Node page: pages.getChildren()) {
            page.setVisible(false);
        }
        academic.setVisible(true);
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
        Student student = (Student) Context.user;
        ArrayList<Position> allPos = student.getPosition();
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
        if(isEarlytoLatest){
            Collections.sort(posCredit,new Comparator<Position>(){
                @Override
                public int compare(Position p1, Position p2){
                    return p1.date.compareTo(p2.date);
                }
            });
            Collections.sort(posNotCre,new Comparator<Position>(){
                @Override
                public int compare(Position p1, Position p2){
                    return p1.date.compareTo(p2.date);
                }
            });
            isEarlytoLatest = false;
        }
        else{
            Collections.sort(posCredit,new Comparator<Position>(){
                @Override
                public int compare(Position p1, Position p2){
                    return p2.date.compareTo(p1.date);
                }
            });
            Collections.sort(posNotCre,new Comparator<Position>(){
                @Override
                public int compare(Position p1, Position p2){
                    return p2.date.compareTo(p1.date);
                }
            });
            isEarlytoLatest = true;
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
    public void exportPosition(ActionEvent event) throws IOException {
        Student student = (Student) Context.user;
        DataIO.exportAchievement(student.getPosition(), student.getName());
        export.getStyleClass().add("success");
        export.setText("Exported!");
        export.setDisable(true);
    }


    @Override
    public void initialize() {
        Student student = (Student) Context.user;
        ArrayList<Position> allPos = student.getPosition();
        ArrayList<Position> posNotCre = new ArrayList<>();
        ArrayList<Position> posCredit = new ArrayList<>();
        if (allPos != null) {
            for (Position tempPos : allPos) {
                if (tempPos.getIsCreditable())
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



