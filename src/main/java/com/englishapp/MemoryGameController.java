package com.englishapp;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class MemoryGameController {

    @FXML private GridPane cardGrid;
    @FXML private Label scoreLabel;
    @FXML private Button restartButton;


    private final List<String> items = List.of("cat", "dog", "apple", "fish");

    private final List<Card> selectedCards = new ArrayList<>();
    private boolean lockBoard = false;
    private int score = 0;
    private int totalPairs = 0;

    private class Card {
        private final String name;
        private final boolean isImage;
        private boolean matched = false;
        private boolean revealed = false;
        private StackPane view;

        Card(String name, boolean isImage) {
            this.name = name;
            this.isImage = isImage;
        }

        StackPane createView() {
            view = new StackPane();
            view.setPrefSize(100, 100);
            view.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
            view.setOnMouseClicked(e -> handleCardClick(this));
            return view;
        }

    void reveal() {
        if (revealed || matched) return;

        RotateTransition firstHalf = new RotateTransition(Duration.millis(150), view);
        firstHalf.setFromAngle(0);
        firstHalf.setToAngle(90);
        firstHalf.setAxis(new Point3D(0, 1, 0));

        RotateTransition secondHalf = new RotateTransition(Duration.millis(150), view);
        secondHalf.setFromAngle(-90); // ← empieza desde -90
        secondHalf.setToAngle(0);     // ← termina en 0
        secondHalf.setAxis(new Point3D(0, 1, 0));

        firstHalf.setOnFinished(e -> {
            view.getChildren().clear();
            if (isImage) {
                Image img = loadImage(name);
                if (img != null) {
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(60);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);
                    view.getChildren().add(imageView);
                } else {
                    view.getChildren().add(new Label("❌"));
                }
            } else {
                Label lbl = new Label(name);
                lbl.setStyle("-fx-font-size: 18px;");
                view.getChildren().add(lbl);
            }
            secondHalf.play();
            revealed = true;
        });

        firstHalf.play();
    }



    void hide() {
        if (!matched && view != null) {
            RotateTransition hideFlip = new RotateTransition(Duration.millis(300), view);
            hideFlip.setFromAngle(180);
            hideFlip.setToAngle(0);
            hideFlip.setAxis(javafx.geometry.Point3D.ZERO.add(0, 1, 0));

            hideFlip.setOnFinished(e -> {
                view.getChildren().clear();
                revealed = false;
            });

            hideFlip.play();
        }
    }


        

        private Image loadImage(String name) {
            String[] extensions = { ".png", ".jpg", ".jpeg" };
            for (String ext : extensions) {
                URL url = getClass().getResource("/com/englishapp/images/" + name + ext);
                if (url != null) {
                    return new Image(url.toExternalForm());
                }
            }
            System.err.println("⚠️ Imagen no encontrada para: " + name);
            return null;
        }

        public String getName() { return name; }
        public boolean isMatched() { return matched; }
        public void setMatched(boolean matched) { this.matched = matched; }
        public boolean isRevealed() { return revealed; }
    }

    @FXML
    public void initialize() {
        generateBoard();
    }
    @FXML
    private void restartGame() {
        generateBoard();
    }


    private void generateBoard() {
        List<Card> cards = new ArrayList<>();
        restartButton.setVisible(false);


        for (String item : items) {
            cards.add(new Card(item, true));
            cards.add(new Card(item, false));
        }

        Collections.shuffle(cards);
        cardGrid.getChildren().clear();
        selectedCards.clear();
        score = 0;
        scoreLabel.setText("Puntaje: 0");
        totalPairs = items.size();

        int row = 0, col = 0;
        for (Card card : cards) {
            StackPane pane = card.createView();
            cardGrid.add(pane, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
    }

    private void handleCardClick(Card card) {
        if (lockBoard || card.isRevealed() || card.isMatched()) return;

        card.reveal();
        selectedCards.add(card);

        if (selectedCards.size() == 2) {
            lockBoard = true;

            Card first = selectedCards.get(0);
            Card second = selectedCards.get(1);

            if (first.getName().equals(second.getName())) {
                first.setMatched(true);
                second.setMatched(true);
                score++;
                scoreLabel.setText("Puntaje: " + score);
                selectedCards.clear();
                lockBoard = false;

                if (score == totalPairs) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("¡Juego completado!");
                    alert.setHeaderText(null);
                    alert.setContentText("¡Felicidades! Has emparejado todas las cartas.");
                    alert.showAndWait();
                    restartButton.setVisible(true);
                }
            } else {
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    first.hide();
                    second.hide();
                    selectedCards.clear();
                    lockBoard = false;
                });
                pause.play();
            }
        }
    }
}
    