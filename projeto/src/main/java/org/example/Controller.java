package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
