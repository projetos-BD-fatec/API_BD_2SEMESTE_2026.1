package org.example.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Modal {

    public static Stage criar(Window owner, String titulo, javafx.scene.Node conteudo) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(owner);
        modal.setTitle(titulo);
        modal.setResizable(false);

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #333;"
        );

        Button btnConfirmar = new Button("Confirmar");
        Button btnCancelar = new Button("Cancelar");

        btnConfirmar.setStyle(
                "-fx-background-color: #1976D2;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 8 20;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        );
        btnCancelar.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-text-fill: #333;" +
                        "-fx-padding: 8 20;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        );

        btnCancelar.setOnAction(e -> modal.close());


        HBox rodape = new HBox(10, btnCancelar, btnConfirmar);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(20, lblTitulo, conteudo, rodape);
        layout.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 30;" +
                        "-fx-min-width: 800;"
        );

        modal.setScene(new Scene(layout));
        return modal;
    }
}