package org.example.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.App;
import org.example.DAO.AulaDAO;
import org.example.DAO.CalendarioDAO;
import org.example.DAO.HorarioDAO;
import org.example.DAO.TopicoDAO;
import org.example.model.Aula;
import org.example.model.Topico;
import org.example.service.AulaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlanejamentoController {
    @FXML private TableView<Aula> tabelaCronograma;
    @FXML private TableColumn<Aula, LocalDate> colData;
    @FXML private TableColumn<Aula, String> colDia;
    @FXML private TableColumn<Aula, String> colEvento;
    @FXML private TableColumn<Aula, String> colHorario;
    @FXML private TableColumn<Aula, String> colConteudo;

    @FXML private TextField        txtTopico;
    @FXML private Spinner<Integer> spinnerMin;
    @FXML private Spinner<Integer> spinnerMax;
    @FXML private ComboBox<String> cbPeso;

    @FXML private VBox containerTopicos;
    @FXML private CheckBox chkAvaliacao;

    @FXML private Label lblAulasTotais;
    @FXML private Label lblAulasPlanejadas;
    @FXML private Label lblAulasRestantes;
    @FXML private Label lblTotalTopicos;
    @FXML private Label lblHoraPlanejada;
    @FXML private Label lblHoraTotal;

    private Long disciplinaIdAtual;

    private final TopicoDAO topicoDAO = new TopicoDAO();
    private final AulaService aulaService = new AulaService(
            new HorarioDAO(), new CalendarioDAO(), new AulaDAO()
    );

    @FXML
    public void initialize() {
        spinnerMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
        spinnerMax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 2));

        cbPeso.getItems().addAll("Peso 1", "Peso 2", "Peso 3");
        cbPeso.setValue("Peso 1");
    }

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
        this.disciplinaIdAtual = disciplinaId;

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

        colConteudo.setCellFactory(col -> new TableCell<Aula, String>() {
            private final ComboBox<String> comboTopico = new ComboBox<>();

            {
                comboTopico.setMaxWidth(Double.MAX_VALUE);
                comboTopico.setPromptText("Selecionar");
                comboTopico.getStyleClass().add("comboTabela");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(comboTopico);
                }
            }
        });

        List<Aula> aulas = aulaService.buscarAulas(disciplinaId);
        tabelaCronograma.getItems().setAll(aulas);

        List<Topico> topicos = topicoDAO.findByDisciplinaId(disciplinaId);
        for (Topico topico : topicos) {
            adicionarLinhaTopico(topico);
        }

        atualizarIndicadores();
    }


    @FXML
    private void clicarIncluir() {
        String nome = txtTopico.getText().trim();
        Integer min     = spinnerMin.getValue();
        Integer max     = spinnerMax.getValue();
        Integer peso    = Integer.parseInt(cbPeso.getValue().replace("Peso ", ""));
        Boolean avaliacao = chkAvaliacao.isSelected();

        if (nome.isEmpty()) {
            mostrarAlerta("Campo obrigatório", "O nome do tópico não pode estar vazio.");
            return;
        }
        if (max < min) {
            mostrarAlerta("Valores inválidos", "O máximo de aulas não pode ser menor que o mínimo.");
            return;
        }

        Topico topico = new Topico(nome, min, max, peso, disciplinaIdAtual, avaliacao);
        try {
            topicoDAO.salvar(topico);
        } catch (SQLException e) {
            mostrarAlerta("Erro no banco", "Não foi possível salvar o tópico: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        adicionarLinhaTopico(topico);
        atualizarIndicadores();

        txtTopico.clear();
        spinnerMin.getValueFactory().setValue(1);
        spinnerMax.getValueFactory().setValue(2);
        cbPeso.setValue("Peso 1");
        chkAvaliacao.setSelected(false);
    }

    private void adicionarLinhaTopico(Topico topico) {
        HBox linha = new HBox(10);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.getStyleClass().add("topico-linha");

        VBox setas = new VBox(2);
        setas.setAlignment(Pos.CENTER);
        setas.setPrefWidth(30);
        Button btnCima  = new Button("▲");
        Button btnBaixo = new Button("▼");
        btnCima.getStyleClass().add("btn-seta");
        btnBaixo.getStyleClass().add("btn-seta");
        btnCima.setOnAction(e -> moverTopico(linha, -1));
        btnBaixo.setOnAction(e -> moverTopico(linha, +1));
        setas.getChildren().addAll(btnCima, btnBaixo);

        Label lblNome = new Label(topico.getNome());
        lblNome.getStyleClass().add("topico-nome");
        HBox.setHgrow(lblNome, Priority.ALWAYS);

        Label lblInfo = new Label(
                "Min:" + topico.getMinAulas() +
                " Máx:" + topico.getMaxAulas() +
                " P:" + topico.getPeso()
        );
        lblInfo.getStyleClass().add("topico-info");

        Label lblBadge = new Label(String.valueOf(topico.getMaxAulas()));
        lblBadge.getStyleClass().add("topico-badge");

        Region spacerInfo = new Region();
        HBox.setHgrow(spacerInfo, Priority.ALWAYS);

        Button btnDeletar = new Button("🗑");
        btnDeletar.getStyleClass().add("btn-deletar");
        btnDeletar.setOnAction(e -> {
            if (topico.getId() != null) {
                try { topicoDAO.deletar(topico.getId()); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            containerTopicos.getChildren().remove(linha);
            atualizarIndicadores();
        });

        linha.getChildren().addAll(setas, lblNome, spacerInfo);

        if (topico.isAvaliacao()) {
            Label lblAvaliacao = new Label("Avaliação");
            lblAvaliacao.getStyleClass().add("topicoAvaliacao");
            linha.getChildren().add(lblAvaliacao);
        }

        linha.getChildren().addAll(lblInfo, lblBadge, btnDeletar);

        containerTopicos.getChildren().add(linha);
    }

    private void moverTopico(HBox linha, int direcao) {
        int idx = containerTopicos.getChildren().indexOf(linha);
        int novoIdx = idx + direcao;
        if (novoIdx >= 0 && novoIdx < containerTopicos.getChildren().size()) {
            containerTopicos.getChildren().remove(linha);
            containerTopicos.getChildren().add(novoIdx, linha);
        }
    }

    private void atualizarIndicadores() {
        List<Aula> aulas = tabelaCronograma.getItems();

        int totais = aulas.size();
        int planejadas = 0;
        for (javafx.scene.Node child : containerTopicos.getChildren()) {
            if (child instanceof HBox) {
                HBox hbox = (HBox) child;
                for (javafx.scene.Node inner : hbox.getChildren()) {
                    if (inner instanceof Label && inner.getStyleClass().contains("topico-badge")) {
                        try {
                            planejadas += Integer.parseInt(((Label) inner).getText());
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }

        int restantes = totais - planejadas;

        lblAulasTotais.setText(String.valueOf(totais));
        lblAulasPlanejadas.setText(String.valueOf(planejadas));
        lblAulasRestantes.setText(String.valueOf(restantes));
        lblTotalTopicos.setText(String.valueOf(containerTopicos.getChildren().size()));

        int totalMinutosPlanejados = planejadas * 50;
        int totalMinutosBase = totais * 50;

        lblHoraPlanejada.setText(formatarHoras(totalMinutosPlanejados));
        lblHoraTotal.setText("/ " + formatarHoras(totalMinutosBase));
    }

    private String formatarHoras(int totalMinutos) {
        int horas = totalMinutos / 60;
        int minutos = totalMinutos % 60;
        return String.format("%02d:%02d", horas, minutos);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
