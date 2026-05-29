package org.example.util;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class DropdownMenu {

    public static Popup criar(Button botaoOrigem, String[][] itens, Consumer<String> aoClicar) {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        VBox box = new VBox(0);
        box.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #000000;" +
                        "-fx-border-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.20), 15, 0, 0, 5);" +
                        "-fx-padding: 8 0;" +
                        "-fx-min-width: 200;"
        );

        for (String[] item : itens) {
            String label = item[0];
            String id    = item[1];
            Button btn   = criarItem(label);
            btn.setOnAction(e -> {
                popup.hide();
                aoClicar.accept(id);
            });
            box.getChildren().add(btn);
        }

        popup.getContent().add(box);
        return popup;
    }

    public static void alternarVisibilidade(Popup popup, Button botaoOrigem) {
        if (popup.isShowing()) {
            popup.hide();
        } else {
            Stage stage = (Stage) botaoOrigem.getScene().getWindow();
            double x = botaoOrigem.localToScreen(0, 0).getX() - 55;
            double y = botaoOrigem.localToScreen(0, 0).getY()
                    + botaoOrigem.getHeight() + 5;
            popup.show(stage, x, y);
        }
    }

    private static Button criarItem(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);

        String estiloNormal =
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #444;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 12 20;" +
                        "-fx-cursor: hand;";

        String estiloHover =
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #F25958;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 12 20;" +
                        "-fx-cursor: hand;";

        btn.setStyle(estiloNormal);
        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloNormal));
        return btn;
    }
}