package com.englishapp;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.*;

public class MathGameController {

    @FXML private Label operationLabel;
    @FXML private HBox optionsBox;
    @FXML private Label scoreLabel;
    @FXML private Label timerLabel;
    @FXML private Label feedbackLabel;
    @FXML private Button restartButton;

    private int score = 0;
    private int timeLeft = 60;
    private Timeline timeline;

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
        restartButton.setVisible(false);
        resetGame();
    }

    private void resetGame() {
        score = 0;
        timeLeft = 60;
        updateScore();
        updateTimerLabel();
        feedbackLabel.setText("");
        restartButton.setVisible(false);
        startTimer();
        generateNewOperation();
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time: " + timeLeft);
    }

    private void startTimer() {
        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            updateTimerLabel();
            if (timeLeft <= 0) {
                timeline.stop();
                showGameOver();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void showGameOver() {
        operationLabel.setText("Time's up!");
        optionsBox.getChildren().clear();
        feedbackLabel.setText("Final Score: " + score);
        feedbackLabel.setTextFill(Color.DARKBLUE);
        restartButton.setVisible(true);
    }

    private void generateNewOperation() {
        optionsBox.getChildren().clear();
        Random rand = new Random();
        int a, b;
        boolean isAddition = rand.nextBoolean();

        if (isAddition) {
            a = rand.nextInt(11);
            b = rand.nextInt(11);
            correctAnswer = a + b;
        } else {
            a = rand.nextInt(11);
            b = rand.nextInt(a + 1);
            correctAnswer = a - b;
        }

        operationLabel.setText(a + (isAddition ? " + " : " - ") + b + " = ?");
        Set<Integer> options = new HashSet<>();
        options.add(correctAnswer);
        while (options.size() < 3) {
            int fake = rand.nextInt(21);
            if (fake != correctAnswer) options.add(fake);
        }

        List<Integer> shuffled = new ArrayList<>(options);
        Collections.shuffle(shuffled);

        for (int opt : shuffled) {
            Button btn = new Button(numberWords.get(opt));
            btn.setPrefWidth(100);
            btn.getStyleClass().add("button");
            btn.setOnAction(e -> checkAnswer(opt, btn));
            optionsBox.getChildren().add(btn);
        }

        playFadeIn();
    }

    private void checkAnswer(int chosen, Button btn) {
        if (chosen == correctAnswer) {
            score++;
            updateScore();
            feedbackLabel.setText("Correct!");
            feedbackLabel.setTextFill(Color.FORESTGREEN);
        } else {
            feedbackLabel.setText("Incorrect!");
            feedbackLabel.setTextFill(Color.CRIMSON);
        }

        ScaleTransition scale = new ScaleTransition(Duration.millis(200), btn);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);
        scale.play();

        FadeTransition fade = new FadeTransition(Duration.millis(500), feedbackLabel);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.8), e -> {
            feedbackLabel.setText("");
            generateNewOperation();
        }));
        delay.play();
    }

    private void playFadeIn() {
        FadeTransition fade = new FadeTransition(Duration.millis(400), optionsBox);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void restartGame() {
        resetGame();
    }
}
