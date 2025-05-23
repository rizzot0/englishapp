package com.englishapp;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderBlocksGameController {

    @FXML
    private VBox container;

    private final List<String> correctOrder = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    @FXML
    public void initialize() {
        loadShuffledDays();
    }

    private void loadShuffledDays() {
        container.getChildren().clear();
        List<String> shuffledDays = new ArrayList<>(correctOrder);
        Collections.shuffle(shuffledDays);

        for (String day : shuffledDays) {
            Button dayButton = createDraggableButton(day);
            container.getChildren().add(dayButton);
        }
    }

    private Button createDraggableButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font(18));
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("day-button");

        button.setOnDragDetected(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(150), button);
            scale.setToX(1.1);
            scale.setToY(1.1);
            scale.setCycleCount(2);
            scale.setAutoReverse(true);
            scale.play();

            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(button.getText());
            db.setContent(content);
            e.consume();
        });

        button.setOnDragOver(e -> {
            if (e.getGestureSource() != button && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        button.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasString()) {
                Button sourceButton = (Button) e.getGestureSource();
                int sourceIndex = container.getChildren().indexOf(sourceButton);
                int targetIndex = container.getChildren().indexOf(button);

                if (sourceIndex != -1 && targetIndex != -1 && sourceIndex != targetIndex) {
                    container.getChildren().remove(sourceButton);
                    container.getChildren().add(targetIndex, sourceButton);
                }

                e.setDropCompleted(true);
            } else {
                e.setDropCompleted(false);
            }
            e.consume();
        });

        return button;
    }

    @FXML
    private void checkOrder() {
        List<String> currentOrder = new ArrayList<>();
        for (var node : container.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                currentOrder.add(btn.getText());
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (currentOrder.equals(correctOrder)) {
            alert.setTitle("Correct!");
            alert.setContentText("Great job! You ordered the days correctly.");
        } else {
            alert.setTitle("Try Again");
            alert.setContentText("The order is incorrect.");
        }
        alert.showAndWait();
    }

    @FXML
    private void restartGame() {
        loadShuffledDays();
    }
}
