package com.englishapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

public class TypingGameController {

    @FXML private ImageView imageView;
    @FXML private Label progressLabel;
    @FXML private Label timerLabel;
    @FXML private VBox gameArea;  

    private List<String> words = List.of("banana", "apple", "grape", "orange", "kiwi");
    private int wordIndex = 0;
    private String currentWord;
    private int currentCharIndex = 0;
    private Timeline timer;
    private int timeLeft = 60;

    @FXML
    public void initialize() {
        gameArea.setOnKeyPressed(this::handleKeyPress);
        gameArea.setFocusTraversable(true);
        loadWord();
        startTimer();
    }

    private void loadWord() {
        currentWord = words.get(wordIndex);
        currentCharIndex = 0;
        updateProgressLabel();
        imageView.setImage(new Image(getClass().getResource("/com/englishapp/images/" + currentWord + ".png").toString()));
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
                wordIndex = (wordIndex + 1) % words.size();
                loadWord();
            } else {
                updateProgressLabel();
            }
        }
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                timer.stop();
                progressLabel.setText("Time's up!");
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
}
