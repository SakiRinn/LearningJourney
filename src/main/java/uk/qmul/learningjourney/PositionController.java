package uk.qmul.learningjourney;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;



public class PositionController {

    @FXML
    private StackPane pages;
    @FXML
    private AnchorPane acedamic;
    @FXML
    private AnchorPane extracuri;

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



}
