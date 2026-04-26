package org.example;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

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
    @FXML
    private TableView<Aula> tabelaAulas;

    @FXML
    private TableColumn<Aula, String> colnDia;

    @FXML
    private TableColumn<Aula, String> colnHorario;

    @FXML
    private TableColumn<Aula, Integer> colnAulas;

    @FXML
    private TableView<Planejamento> tabelaCronograma;

    @FXML
    private TableColumn<Planejamento, String> colData;

    @FXML
    private TableColumn<Planejamento, String> colDia;

    @FXML
    private TableColumn<Planejamento, String> colHorario;

    @FXML
    private TableColumn<Planejamento, String> colEvento;

    @FXML
    private TableColumn<Planejamento, String> colConteudo;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (colData != null){
            colData.setCellValueFactory(new PropertyValueFactory<>("data"));
            colDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
            colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
            colEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
            colConteudo.setCellValueFactory(new PropertyValueFactory<>("conteudo"));
            tabelaCronograma.setItems(getDadosExemplo());
        }
        if (tabelaAulas != null) {

            colnDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
            colnHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
            colnAulas.setCellValueFactory(new PropertyValueFactory<>("aulas"));

            tabelaAulas.setItems(FXCollections.observableArrayList(
                    new Aula("Segunda", "18:45 - 19:35", 1),
                    new Aula("Quarta", "18:45 - 20:25", 3)
            ));
        }
    }

    private ObservableList<Planejamento> getDadosExemplo() {
        return FXCollections.observableArrayList(
                new Planejamento("02/03/2026","Segunda","18:45 - 19:35","Letivo","Selecionar"),
                new Planejamento("04/03/2026","Quarta","18:45 - 19:35","Letivo","Selecionar"),
                new Planejamento("04/03/2026","Quarta","19:35 - 20:25","Letivo","Selecionar")
                );
    }

}
