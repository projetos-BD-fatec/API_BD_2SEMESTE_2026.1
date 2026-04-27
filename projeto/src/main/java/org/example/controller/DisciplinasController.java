package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.App;
import org.example.model.DiaSemana;
import org.example.model.Horario;
import java.time.LocalTime;

public class DisciplinasController {

    @FXML private ComboBox<DiaSemana> cbDia;
    @FXML private ComboBox<LocalTime> cbInicio;
    @FXML private ComboBox<LocalTime> cbFim;
    @FXML private Button btnAdicionar;

    @FXML private TableView<Horario> tabelaHorarios;
    @FXML private TableColumn<Horario, String> colDia;
    @FXML private TableColumn<Horario, String> colInicio;
    @FXML private TableColumn<Horario, String> colFim;

    private final ObservableList<Horario> listaHorarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1. Preenche os dias da semana
        cbDia.setItems(FXCollections.observableArrayList(DiaSemana.values()));

        // 2. Cria a lista de horários manuais da FATEC
        ObservableList<LocalTime> listaDeHoras = FXCollections.observableArrayList(
                LocalTime.of(18, 45),
                LocalTime.of(19, 35),
                LocalTime.of(20, 25),
                LocalTime.of(21, 25),
                LocalTime.of(22, 15),
                LocalTime.of(23, 0)
        );
        // Toda vez que o usuário escolher um horário de Início...
        cbInicio.valueProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (valorNovo != null) {
                // Criamos uma nova lista filtrada apenas com horários DEPOIS do início
                ObservableList<LocalTime> horariosFiltrados = listaDeHoras.filtered(horario ->
                        horario.isAfter(valorNovo)
                );

                // Atualizamos as opções do campo de Fim
                cbFim.setItems(horariosFiltrados);

                // Se o horário de fim que já estava selecionado for inválido, limpamos ele
                if (cbFim.getValue() != null && !cbFim.getValue().isAfter(valorNovo)) {
                    cbFim.setValue(null);
                }
            }
        });

        // 3. Alimenta os ComboBoxes
        cbInicio.setItems(listaDeHoras);
        cbFim.setItems(listaDeHoras);

        // 4. Configura as colunas da tabela
        colDia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDiaSemana().getValorBanco()));
        colInicio.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getHoraInicio().toString()));
        colFim.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getHoraFim().toString()));

        // 5. Conecta a lista à tabela
        tabelaHorarios.setItems(listaHorarios);
    }

    @FXML
    private void clicarBtnAdicionar() {
        DiaSemana dia = cbDia.getValue();
        LocalTime inicio = cbInicio.getValue();
        LocalTime fim = cbFim.getValue();

        if (dia == null || inicio == null || fim == null) return;
        if (!fim.isAfter(inicio)) return;

        Horario horario = new Horario(null, null, dia, inicio, fim);
        listaHorarios.add(horario);
    }

    @FXML
    private void clicarDisciplina() {
        try {
            App.navegarParaPlanejamento(1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}