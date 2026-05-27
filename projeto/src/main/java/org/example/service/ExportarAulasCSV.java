package org.example.service;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.model.Aula;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

public class ExportarAulasCSV {

    private final TableView<Aula> tableView;

    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public ExportarAulasCSV(TableView<Aula> tableView) {
        this.tableView = tableView;
    }

    // -------------------------------------------------------

    // -------------------------------------------------------
    public void exportar(Stage stage) {

        // 1. Diálogo "Salvar como"
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar arquivo CSV");
        fileChooser.setInitialFileName("aulas.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivo CSV (*.csv)", "*.csv")
        );

        File arquivo = fileChooser.showSaveDialog(stage);
        if (arquivo == null) return; // usuário cancelou

        // 2. Grava o arquivo em UTF-8
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {

            // Cabeçalho
            pw.println("Data;Dia da Semana;Evento;Horário;Conteúdo");

            // Uma linha por Aula
            for (Aula aula : tableView.getItems()) {

                // Data: já formatada pela tabela (dd/MM/yyyy)
                String data = "";
                for (TableColumn<Aula, ?> col : tableView.getColumns()) {
                    if (col.getText().equalsIgnoreCase("Data")) {
                        Object valor = col.getCellData(aula);
                        data = valor != null ? valor.toString() : "";
                        break;
                    }
                }

                String diaSemana = aula.getDiaSemana() != null
                        ? aula.getDiaSemana().toString() : "";

                String evento = aula.getEvento() != null ? aula.getEvento() : "";

                // Horário: une horaInicio e horaFim → "18:45 - 19:35"
                String horario = "";
                if (aula.getHoraInicio() != null && aula.getHoraFim() != null) {
                    horario = aula.getHoraInicio().format(FMT_HORA)
                            + " - "
                            + aula.getHoraFim().format(FMT_HORA);
                }

                // Conteúdo: lê o valor exibido na coluna "Conteúdo" da TableView
                String conteudo = "";
                for (TableColumn<Aula, ?> col : tableView.getColumns()) {
                    if (col.getText().equalsIgnoreCase("Conteúdo")) {
                        Object valor = col.getCellData(aula);
                        conteudo = valor != null ? valor.toString() : "";
                        break;
                    }
                }

                pw.println(String.join(";",
                        escaparCSV(data),
                        escaparCSV(diaSemana),
                        escaparCSV(evento),
                        escaparCSV(horario),
                        escaparCSV(conteudo)
                ));
            }

            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Exportação concluída",
                    "Arquivo salvo com sucesso em:\n" + arquivo.getAbsolutePath());

        } catch (IOException ex) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Erro ao exportar",
                    "Não foi possível salvar o arquivo:\n" + ex.getMessage());
        }
    }

    private String escaparCSV(String valor) {
        if (valor == null) return "";
        if (valor.contains(";") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}