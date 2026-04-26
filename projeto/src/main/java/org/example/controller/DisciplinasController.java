package org.example.controller;

import javafx.fxml.FXML;
import org.example.App;

public class DisciplinasController {
    @FXML
    private void clicarDisciplina() {
        try {
            App.navegarParaPlanejamento(1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
