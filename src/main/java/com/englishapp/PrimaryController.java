package com.englishapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


    @FXML
    private void switchToOrderBlocksGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderBlocksGame.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Order the Days of the Week");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
    