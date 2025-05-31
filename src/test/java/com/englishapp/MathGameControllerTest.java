package com.englishapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathGameControllerTest {

    private MathGameController controller;

    @BeforeEach
    public void setUp() {
        controller = new MathGameController();
    }

    @Test
    public void testInitialScoreIsZero() {
        assertEquals(0, controller.getScore(), "El puntaje inicial debe ser 0 al comenzar");
    }

    @Test
    public void testScoreIncrementsCorrectly() {
        controller.incrementScore();
        assertEquals(1, controller.getScore(), "El puntaje debe ser 1 luego de incrementScore()");
    }

    @Test
    public void testCheckCorrectAnswerIncrementsScore() {
        controller.incrementScore(); // score = 1
        int startingScore = controller.getScore();
        controller.setCorrectAnswer(5);
        controller.checkAnswerPublic(5); // respuesta correcta
        assertEquals(startingScore + 1, controller.getScore(), "El puntaje debe aumentar si la respuesta es correcta");
    }

    @Test
    public void testCheckWrongAnswerDoesNotChangeScore() {
        int startingScore = controller.getScore();
        controller.setCorrectAnswer(5);
        controller.checkAnswerPublic(3); // respuesta incorrecta
        assertEquals(startingScore, controller.getScore(), "El puntaje no debe cambiar si la respuesta es incorrecta");
    }

    @Test
    public void testCorrectAnswerWithinValidRange() {
        for (int i = 0; i < 100; i++) { // Probar múltiples veces
            controller.generateTestOperation();
            int answer = controller.getCorrectAnswer();
            assertTrue(answer >= 0 && answer <= 20, "La respuesta debe estar entre 0 y 20");
        }
    }

    

}
