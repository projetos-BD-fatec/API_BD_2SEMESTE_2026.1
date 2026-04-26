package org.example.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import org.example.App;

public class Controller {

    @FXML
    private void clicarBtnVoltar() {
        try {
            App.setRoot("TelaDisciplinas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicarDisciplina() {
        try {
            App.setRoot("TelaPlanejamento");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ComboBox<String> cbEscolherDisciplina;

    @FXML
    private ComboBox<String> cbEscolherTurma;


    @FXML
    void escolherDicilplina(ActionEvent event) {

    }

    @FXML
    void escolherTurma(ActionEvent event) {

    }

}
