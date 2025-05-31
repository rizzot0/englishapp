package com.englishapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

public class MathGameControllerUITest extends ApplicationTest {

    private MathGameController controller;

    @Override
    public void start(javafx.stage.Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/englishapp/MathGame.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUpTest() {
        // Puedes hacer setup adicional aquí si lo necesitas
    }

    @Test
    public void testRestartButtonInitiallyHidden() {
        assertFalse(controller.getRestartButton().isVisible(), "El botón de reinicio debe estar oculto al inicio");
    }
}
