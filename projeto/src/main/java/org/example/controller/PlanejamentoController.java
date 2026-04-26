package org.example.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.App;
import org.example.DAO.AulaDAO;
import org.example.DAO.CalendarioDAO;
import org.example.DAO.HorarioDAO;
import org.example.model.Aula;
import org.example.model.DiaSemana;
import org.example.service.AulaService;

import java.time.LocalDate;
import java.util.List;

public class PlanejamentoController {
    @FXML private TableView<Aula> tabelaCronograma;
    @FXML private TableColumn<Aula, LocalDate> colData;
    @FXML private TableColumn<Aula, String> colDia;
    @FXML private TableColumn<Aula, String> colEvento;
    @FXML private TableColumn<Aula, String> colHorario;
    @FXML private TableColumn<Aula, String> colConteudo;

    @FXML
    private void clicarVoltar() {
        try {
            App.setRoot("TelaDisciplinas");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final AulaService aulaService = new AulaService(
            new HorarioDAO(), new CalendarioDAO(), new AulaDAO()
    );

    public void setDisciplinaId(Long disciplinaId) {
        colData.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getData()));
        colDia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDiaSemana().getValorBanco()));
        colEvento.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getEvento()));
        colHorario.setCellValueFactory(cell -> {
            Aula aula = cell.getValue();
            String horario = aula.getHoraInicio() + " - " + aula.getHoraFim();
            return new SimpleStringProperty(horario);
        });
        colConteudo.setCellValueFactory(cell -> new SimpleStringProperty(""));
        List<Aula> aulas = aulaService.buscarAulas(disciplinaId);
        tabelaCronograma.getItems().setAll(aulas);
    }

}
