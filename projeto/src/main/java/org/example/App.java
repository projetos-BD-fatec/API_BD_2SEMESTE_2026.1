package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.controller.PlanejamentoController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private static boolean alteracaoNaoSalva = false;
    private static Runnable salvarCallback = () -> {};
    private static Runnable descartarCallback = () -> {};

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("TelaDisciplinas"), 960, 540);
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            if (alteracaoNaoSalva) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Salvar alterações");
                alert.setHeaderText("Você tem alterações não salvas.");
                alert.setContentText("Deseja salvar antes de sair?");

                ButtonType salvar = new ButtonType("Salvar e Sair");
                ButtonType sair = new ButtonType("Sair sem Salvar");
                ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(salvar, sair, cancelar);

                alert.showAndWait().ifPresent(resposta -> {
                    if (resposta == salvar) {
                        salvarCallback.run();
                        stage.close();
                    } else if (resposta == sair) {
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

        PlanejamentoController controller = loader.getController();
        controller.setDisciplinaId(disciplinaId);
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

    public static void main(String[] args) {
        launch();
    }

}