package com.englishapp;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.*;

public class MathGameController {

    @FXML private Label operationLabel;
    @FXML private HBox optionsBox;
    @FXML private Label scoreLabel;

    private int score = 0;

    private final Map<Integer, String> numberWords = Map.ofEntries(
        Map.entry(0, "zero"), Map.entry(1, "one"), Map.entry(2, "two"),
        Map.entry(3, "three"), Map.entry(4, "four"), Map.entry(5, "five"),
        Map.entry(6, "six"), Map.entry(7, "seven"), Map.entry(8, "eight"),
        Map.entry(9, "nine"), Map.entry(10, "ten"), Map.entry(11, "eleven"),
        Map.entry(12, "twelve"), Map.entry(13, "thirteen"), Map.entry(14, "fourteen"),
        Map.entry(15, "fifteen"), Map.entry(16, "sixteen"), Map.entry(17, "seventeen"),
        Map.entry(18, "eighteen"), Map.entry(19, "nineteen"), Map.entry(20, "twenty")
    );

    private int correctAnswer;

    @FXML
    public void initialize() {
        score = 0;
        updateScore();
        generateNewOperation();
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    private void generateNewOperation() {
        optionsBox.getChildren().clear();
        Random rand = new Random();
        int a = rand.nextInt(11);
        int b = rand.nextInt(11);
        boolean isAddition = rand.nextBoolean();
        correctAnswer = isAddition ? a + b : a - b;
        correctAnswer = Math.max(0, correctAnswer);

        operationLabel.setText(a + (isAddition ? " + " : " - ") + b + " = ?");

        Set<Integer> options = new HashSet<>();
        options.add(correctAnswer);
        while (options.size() < 3) {
            int fake = rand.nextInt(21);
            options.add(fake);
        }

        List<Integer> shuffled = new ArrayList<>(options);
        Collections.shuffle(shuffled);

        for (int opt : shuffled) {
            Button btn = new Button(numberWords.get(opt));
            btn.setOnAction(e -> checkAnswer(opt));
            btn.setStyle("-fx-font-size: 18px; -fx-background-radius: 10;");
            optionsBox.getChildren().add(btn);
        }

        playFadeIn();
    }

    private void checkAnswer(int chosen) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (chosen == correctAnswer) {
            alert.setTitle("Correct!");
            alert.setContentText("Well done! That's the correct answer.");
            score++;
            updateScore();
        } else {
            alert.setTitle("Oops!");
            alert.setContentText("That's not quite right. Try again next time!");
        }

        alert.setOnHidden(e -> generateNewOperation());
        alert.show();
    }

    private void playFadeIn() {
        FadeTransition fade = new FadeTransition(Duration.millis(400), optionsBox);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
