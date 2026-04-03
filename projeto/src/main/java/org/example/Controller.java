package org.example;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;


public class Controller {

    @FXML
    private ComboBox<String> cbEscolherDisciplina;

    @FXML
    private ComboBox<String> cbEscolherTurma;

    @FXML
    private TableView<Object> tabelaCronograma;


@FXML
void escolherDicilplina(ActionEvent event) {

}

@FXML
void escolherTurma(ActionEvent event) {

}
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
