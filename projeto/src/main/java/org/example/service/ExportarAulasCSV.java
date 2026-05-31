package org.example.service;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.model.Aula;
import org.example.model.Topico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportarAulasCSV {

    private final TableView<Aula> tableView;
    private final List<Topico> topicosCache;

    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public ExportarAulasCSV(TableView<Aula> tableView, List<Topico> topicosCache) {
        this.tableView = tableView;
        this.topicosCache = topicosCache;
    }

    public void exportar(Stage stage) {

        // 1. Diálogo "Salvar como"
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar arquivo CSV");
        fileChooser.setInitialFileName("aulas.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivo CSV (*.csv)", "*.csv")
        );

        File arquivo = fileChooser.showSaveDialog(stage);
        if (arquivo == null) return;

        // 2. Grava o arquivo em UTF-8 com BOM (corrige acentos no Excel)
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {

            // BOM — faz o Excel reconhecer UTF-8 corretamente
            pw.print('\uFEFF');

            // Cabeçalho
            pw.println("Data;Dia da Semana;Evento;Horário;Conteúdo");

            for (Aula aula : tableView.getItems()) {

                // Data: já formatada pela tabela (dd/MM/yyyy)
                String data = aula.getData() != null ? aula.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";

                // Dia da semana
                String diaSemana = aula.getDiaSemana() != null
                        ? aula.getDiaSemana().getValorBanco() : "";

                // Evento
                String evento = aula.getEvento() != null ? aula.getEvento() : "";

                // Horário
                String horario = "";
                if (aula.getHoraInicio() != null && aula.getHoraFim() != null) {
                    horario = aula.getHoraInicio().format(FMT_HORA)
                            + " - "
                            + aula.getHoraFim().format(FMT_HORA);
                }

                // Conteúdo: busca o nome do tópico pelo topicoId
                String conteudo = "";
                if (aula.getTopicoId() != null && topicosCache != null) {
                    conteudo = topicosCache.stream()
                            .filter(t -> t.getId().equals(aula.getTopicoId()))
                            .map(Topico::getNome)
                            .findFirst()
                            .orElse("");
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
