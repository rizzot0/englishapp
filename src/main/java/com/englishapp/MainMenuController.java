package com.englishapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private void openOrderBlocksGame() throws IOException {
        loadScene("OrderBlocksGame.fxml", "Ordenar los días");
    }

    @FXML
    private void openTypingGame() throws IOException {
        loadScene("TypingGame.fxml", "Batalla de tipeo");
    }

    @FXML
    private void openSpellingGame() throws IOException {
        loadScene("SpellingGame.fxml", "Deletreo");
    }

    @FXML
    private void openMathGame() throws IOException {
        loadScene("MathGame.fxml", "Matemáticas");
    }

    private void loadScene(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
