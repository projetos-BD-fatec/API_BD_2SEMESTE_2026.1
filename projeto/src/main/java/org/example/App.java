package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.controller.DisciplinasController;
import org.example.controller.PlanejamentoController;

import java.io.IOException;
import java.util.Optional;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private static boolean alteracaoNaoSalva = false;
    private static Runnable salvarCallback = () -> {};
    private static Runnable descartarCallback = () -> {};

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("TelaLoginCadastro"), 960, 540);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if (alteracaoNaoSalva) {
                event.consume();
                Optional<ButtonType> resposta = mostrarConfirmarAlteracoes("Salvar alterações", "Você tem alterações não salvas.", "Deseja salvar antes de sair?");
                resposta.ifPresent(btn -> {
                    if (btn.getText().equals("Salvar e Sair")) {
                        salvarCallback.run();
                        stage.close();

                    } else if (btn.getText().equals("Sair sem Salvar")) {
                        descartarCallback.run();
                        alteracaoNaoSalva = false;
                        stage.close();
                    }
                });
            }
        });

        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void navegarParaPlanejamento(Long disciplinaId) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("TelaPlanejamento.fxml"));
        Parent root = loader.load();
        scene.setRoot(root);
        PlanejamentoController controller = loader.getController();
        controller.setDisciplinaId(disciplinaId);
    }

    public static void navegarParaDisciplinas() throws IOException {
        Parent root = new FXMLLoader(App.class.getResource("TelaDisciplinas.fxml")).load();
        scene.setRoot(root);
    }

    public static void sairParaLogin() throws IOException {
        Parent root = new FXMLLoader(App.class.getResource("TelaLoginCadastro.fxml")).load();
        scene.setRoot(root);
    }


    public static void setAlteracaoNaoSalva(boolean value) {
        alteracaoNaoSalva = value;
    }

    public static boolean isAlteracaoNaoSalva() {
        return alteracaoNaoSalva;
    }

    public static void setSalvarCallback(Runnable callback) {
        salvarCallback = callback;
    }

    public static void setDescartarCallback(Runnable callback) {
        descartarCallback = callback;
    }

    public static Optional<ButtonType> mostrarConfirmarAlteracoes(String titulo, String cabecalho, String mensagem) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);
        final ButtonType[] resposta = new ButtonType[1];

        Label lblTitulo = new Label("⚠ " + titulo);
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: #F5E6CF;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-padding: 12 18;"
        );
        Label lblCabecalho = new Label(cabecalho);
        lblCabecalho.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 18 0 18;"
        );
        Label lblMensagem = new Label(mensagem);
        lblMensagem.setWrapText(true);
        lblMensagem.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-padding: 12 18 18 18;"
        );
        Button btnSalvar = new Button("Salvar e Sair");
        btnSalvar.setStyle(
                "-fx-background-color: #6D9D7B;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-padding: 8 18;" +
                        "-fx-cursor: hand;"
        );

        Button btnSair = new Button("Sair sem Salvar");
        btnSair.setStyle(
                "-fx-background-color: #F25958;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-padding: 8 18;" +
                        "-fx-cursor: hand;"
        );
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-padding: 8 18;" +
                        "-fx-cursor: hand;"
        );
        btnSalvar.setOnAction(e -> {
            resposta[0] = new ButtonType("Salvar e Sair");
            modal.close();
        });
        btnSair.setOnAction(e -> {
            resposta[0] = new ButtonType("Sair sem Salvar");
            modal.close();
        });
        btnCancelar.setOnAction(e -> modal.close());
        HBox botoes = new HBox(10, btnCancelar, btnSair, btnSalvar);
        botoes.setAlignment(Pos.CENTER_RIGHT);
        botoes.setPadding(new Insets(18));
        VBox layout = new VBox(lblTitulo, lblCabecalho, lblMensagem, botoes);
        layout.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2 5 5 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;"
        );
        modal.setScene(new Scene(layout));
        modal.showAndWait();
        return Optional.ofNullable(resposta[0]);
    }

    public static void main(String[] args) {
        launch(args);
    }

}