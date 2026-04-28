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
import org.example.util.DadosFixos;

import java.time.LocalTime;
import java.util.List;

public class DisciplinasController {

    @FXML private ComboBox<DiaSemana> cbDia;
    @FXML private ComboBox<LocalTime> cbInicio;
    @FXML private ComboBox<LocalTime> cbFim;
    @FXML private TableView<Horario> tabelaHorarios;
    @FXML private TableColumn<Horario, String> colDia;
    @FXML private TableColumn<Horario, String> colInicio;
    @FXML private TableColumn<Horario, String> colFim;

    private final ObservableList<Horario> listaHorarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbDia.getItems().setAll(DiaSemana.values());

        cbInicio.getItems().setAll(DadosFixos.HORARIOS);

        cbFim.getItems().setAll(DadosFixos.HORARIOS);


        cbInicio.valueProperty().addListener((obs, antigo, novo) -> {

            if (novo != null) {

                ObservableList<LocalTime> horariosFiltrados =
                        FXCollections.observableArrayList(
                                DadosFixos.HORARIOS.filtered(
                                        h -> h.isAfter(novo)
                                )
                        );

                cbFim.setItems(horariosFiltrados);

                if (cbFim.getValue() != null &&
                        !cbFim.getValue().isAfter(novo)) {

                    cbFim.setValue(null);
                }
            }
        });

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