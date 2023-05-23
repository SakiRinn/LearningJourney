package uk.qmul.learningjourney.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Position;

import java.io.IOException;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static uk.qmul.learningjourney.util.CourseUtil.generateAchievementWord;


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

    public Boolean isEarlytoLatest = false;
    private class DateComparator implements Comparator<Position>{
        @Override
        public int compare(Position o1, Position o2) {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd");
            try {
                Date date1 = dateformat.parse(o1.getDate());
                Date date2 = dateformat.parse(o2.getDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }


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
        ArrayList<Position> allPos = Context.account.getPosition();
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
        generateAchievementWord(Context.account.getPosition());
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Position> allPos = Context.account.getPosition();
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



