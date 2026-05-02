package org.example.util;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {
    public static void mostrar(Stage stage, String mensagem) {
        Label label = new Label(mensagem);
        label.setStyle(
                "-fx-background-color: #F25958;" +
                        "-fx-text-fill: white;" +
                        "-fx-alignment: center;" +
                        "-fx-padding: 12 20 12 20;" +
                        "-fx-background-radius: 8;" +
                        "-fx-font-size: 18px;" +
                        "-fx-wrap-text: true;" +
                        "-fx-max-width: 360px;"
        );

        Popup popup = new Popup();
        popup.getContent().add(label);
        popup.setAutoFix(true);

        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() - popup.getWidth() - 20);
            popup.setY(stage.getY() + stage.getHeight() - popup.getHeight() - 40);
        });

        popup.show(stage);

        FadeTransition fade = new FadeTransition(Duration.seconds(1), label);
        fade.setDelay(Duration.seconds(3.5));
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> popup.hide());
        fade.play();
    }
}