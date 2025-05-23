package com.englishapp;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;
import java.util.Collections;

public class TypingGameController {

    @FXML private ImageView imageView;
    @FXML private Label progressLabel;
    @FXML private Label timerLabel;
    @FXML private VBox gameArea;
    @FXML private Button restartButton;
    @FXML private Label scoreLabel;

    private final List<String> baseWords = List.of(
        "banana", "apple", "grape", "orange", "kiwi",
        "pear", "lemon", "mango", "peach", "cherry", "pineapple"
    );

    private List<String> words;
    private int wordIndex = 0;
    private String currentWord;
    private int currentCharIndex = 0;
    private Timeline timer;
    private int timeLeft;

    private int score = 0;

    @FXML
    public void initialize() {
        gameArea.setOnKeyPressed(this::handleKeyPress);
        gameArea.setFocusTraversable(true);

        scoreLabel.getStyleClass().add("score-label");
        resetGame();
    }

    private void resetGame() {
        if (timer != null) timer.stop();

        words = new java.util.ArrayList<>(baseWords);
        Collections.shuffle(words);
        wordIndex = 0;
        currentCharIndex = 0;
        score = 0;
        timeLeft = 60;

        scoreLabel.setText("Puntaje: 0");
        timerLabel.setText("Tiempo: 60");

        restartButton.setVisible(false);
        loadWord();
        startTimer();
    }

    @FXML
    private void restartGame() {
        resetGame();
    }

    private void loadWord() {
        currentWord = words.get(wordIndex);
        currentCharIndex = 0;
        updateProgressLabel();

        imageView.setImage(
            new Image(getClass().getResource("/com/englishapp/images/" + currentWord + ".png").toString())
        );

        FadeTransition fade = new FadeTransition(Duration.millis(400), imageView);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void updateProgressLabel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currentWord.length(); i++) {
            if (i < currentCharIndex) {
                sb.append(currentWord.charAt(i)).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        progressLabel.setText(sb.toString());
    }

    private void handleKeyPress(KeyEvent e) {
        if (timeLeft <= 0) return;

        String pressed = e.getText();
        if (pressed.length() == 1 && pressed.charAt(0) == currentWord.charAt(currentCharIndex)) {
            currentCharIndex++;
            if (currentCharIndex == currentWord.length()) {
                score++;
                scoreLabel.setText("Puntaje: " + score);
                playSuccessAnimation(progressLabel);

                wordIndex = (wordIndex + 1) % words.size();
                loadWord();
            } else {
                updateProgressLabel();
            }
        }
    }

    private void startTimer() {
        if (timer != null) timer.stop();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                timeLeft = 0;
                timerLabel.setText("Tiempo: 0");
                timer.stop();

                progressLabel.setText("¡Tiempo agotado!");
                restartButton.setVisible(true);
                playBounceAnimation(restartButton);
            } else {
                timerLabel.setText("Tiempo: " + timeLeft);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void playSuccessAnimation(Label label) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), label);
        scale.setFromX(1);
        scale.setToX(1.4);
        scale.setFromY(1);
        scale.setToY(1.4);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private void playBounceAnimation(Button button) {
        ScaleTransition bounce = new ScaleTransition(Duration.millis(300), button);
        bounce.setFromX(0);
        bounce.setToX(1);
        bounce.setFromY(0);
        bounce.setToY(1);
        bounce.setInterpolator(Interpolator.EASE_OUT);
        bounce.play();
    }
}
