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
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.AulaDAO;
import org.example.DAO.CalendarioDAO;
import org.example.DAO.HorarioDAO;
import org.example.DAO.TopicoDAO;
import org.example.model.Aula;
import org.example.model.DiaSemana;
import org.example.model.Topico;
import org.example.service.AulaService;
import org.example.service.DistribuicaoService;
import org.example.util.Toast;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlanejamentoController {
    @FXML private TableView<Aula> tabelaCronograma;
    @FXML private TableColumn<Aula, LocalDate> colData;
    @FXML private TableColumn<Aula, String> colDia;
    @FXML private TableColumn<Aula, String> colEvento;
    @FXML private TableColumn<Aula, String> colHorario;
    @FXML private TableColumn<Aula, String> colConteudo;

    @FXML private TextField txtTopico;
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

    private List<Topico> topicosCache = new ArrayList<>();
    private List<Topico> topicosPendentes = new ArrayList<>();
    private final TopicoDAO topicoDAO = new TopicoDAO();
    private final AulaDAO aulaDAO = new AulaDAO();
    private final AulaService aulaService = new AulaService(
            new HorarioDAO(), new CalendarioDAO(), new AulaDAO()
    );
    private final DistribuicaoService distribuicaoService = new DistribuicaoService();

    private List<Aula> aulasCronograma = new ArrayList<>();

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
            if (App.isAlteracaoNaoSalva()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alterações não salvas");
                alert.setHeaderText("Você tem alterações não salvas.");
                alert.setContentText("O que deseja fazer?");

                ButtonType salvar = new ButtonType("Salvar");
                ButtonType descartar = new ButtonType("Descartar alterações");
                ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(salvar, descartar, cancelar);

                alert.showAndWait().ifPresent(resposta -> {
                    if (resposta == salvar) {
                        clicarSalvar();
                        navegarParaDisciplinas();
                    } else if (resposta == descartar) {
                        descartarAlteracoes();
                        navegarParaDisciplinas();
                    }
                });
            } else {
                navegarParaDisciplinas();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void navegarParaDisciplinas() {
        try {
            App.setAlteracaoNaoSalva(false);
            App.setSalvarCallback(() -> {});
            App.setDescartarCallback(() -> {});
            App.setRoot("TelaDisciplinas");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setDisciplinaId(Long disciplinaId) {
        App.setSalvarCallback(this::clicarSalvar);
        App.setDescartarCallback(this::descartarAlteracoes);
        this.disciplinaIdAtual = disciplinaId;

        colData.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getData()));
        colDia.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDiaSemana().getValorBanco()));
        colEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEvento()));
        colHorario.setCellValueFactory(cell -> {
            Aula aula = cell.getValue();
            return new SimpleStringProperty(aula.getHoraInicio() + " - " + aula.getHoraFim());
        });

        topicosCache = topicoDAO.findByDisciplinaId(disciplinaId);
        colConteudo.setCellFactory(col -> new TableCell<Aula, String>() {
            private final ComboBox<String> comboTopico = new ComboBox<>();
            private boolean atualizandoProgramaticamente = false;

            {
                comboTopico.setMaxWidth(Double.MAX_VALUE);
                comboTopico.setPromptText("Selecionar");
                comboTopico.getStyleClass().add("comboTabela");
                comboTopico.valueProperty().addListener((obs, antigoValor, novoValor) -> {
                    if (atualizandoProgramaticamente) return;
                    if (getTableRow() == null || getTableRow().getItem() == null) return;

                    Aula aula = getTableRow().getItem();

                    if (aula.getDiaSemana() == DiaSemana.SABADO) return;
                    if (novoValor == null || novoValor.equals("Selecionar")) {
                        aula.setAncorada(false);
                        aula.setTopicoId(null);
                        redistribuir();
                        return;
                    }

                    topicosCache.stream()
                            .filter(t -> t.getNome().equals(novoValor))
                            .findFirst()
                            .ifPresent(t -> {
                                long ancorasDessTopico = aulasCronograma.stream()
                                        .filter(a -> a.isAncorada() && t.getId().equals(a.getTopicoId()) && !a.getId().equals(aula.getId()))
                                        .count();

                                if (ancorasDessTopico >= t.getMaxAulas()) {
                                    mostrarAlerta("Limite atingido",
                                            "O tópico \"" + t.getNome() + "\" já atingiu o máximo de " + t.getMaxAulas() + " aulas fixadas manualmente.");
                                    atualizandoProgramaticamente = true;
                                    comboTopico.setValue(antigoValor);
                                    atualizandoProgramaticamente = false;
                                    return;
                                }

                                aula.setTopicoId(t.getId());
                                aula.setAncorada(true);
                                redistribuir();
                            });
                    atualizarIndicadores();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Aula aula = getTableRow().getItem();

                    if (aula.getDiaSemana() == DiaSemana.SABADO) {
                        Label lblFechamento = new Label("Fechamento");
                        lblFechamento.setStyle("-fx-font-style: italic;");
                        setGraphic(lblFechamento);
                        setText(null);
                        return;
                    }

                    boolean isEventoReservado = distribuicaoService.isDiaBloqueado(aula);

                    List<Topico> todosTopicos = topicosCache;
                    List<String> nomesFiltrados;

                    if (isEventoReservado) {
                        nomesFiltrados = todosTopicos.stream()
                                .filter(t -> !t.isAvaliacao())
                                .map(Topico::getNome)
                                .collect(Collectors.toList());
                    } else {
                        nomesFiltrados = todosTopicos.stream()
                                .map(Topico::getNome)
                                .collect(Collectors.toList());
                    }

                    if (nomesFiltrados.isEmpty()) {
                        setGraphic(null);

                        setText(isEventoReservado ? "Sem tópicos comuns" : "Sem tópicos cadastrados");
                    } else {
                        atualizandoProgramaticamente = true;
                        nomesFiltrados.add(0, "Selecionar");
                        comboTopico.getItems().setAll(nomesFiltrados);
                        if (aula.getTopicoId() != null) {
                            topicosCache.stream()
                                    .filter(t -> t.getId().equals(aula.getTopicoId()))
                                    .findFirst()
                                    .ifPresent(t -> comboTopico.setValue(t.getNome()));
                        } else {
                            comboTopico.setValue("Selecionar");
                        }
                        atualizandoProgramaticamente = false;
                        setGraphic(comboTopico);
                        setText(null);
                    }
                }
            }
        });

        aulasCronograma = aulaService.buscarAulas(disciplinaId);
        aulasCronograma.stream()
                .filter(a -> a.getDiaSemana() == DiaSemana.SABADO)
                .forEach(a -> {
                    a.setTopicoId(999L);
                    a.setAncorada(true);
                });

        tabelaCronograma.getItems().setAll(aulasCronograma);
        containerTopicos.getChildren().clear();
        topicosCache.forEach(this::adicionarLinhaTopico);
        atualizarIndicadores();
    }


    @FXML
    private void clicarIncluir() {
        String nome = txtTopico.getText().trim();
        Integer min = spinnerMin.getValue();
        Integer max = spinnerMax.getValue();
        Integer peso = Integer.parseInt(cbPeso.getValue().replace("Peso ", ""));
        Boolean avaliacao = chkAvaliacao.isSelected();

        if (nome.isEmpty()) {
            mostrarAlerta("Campo obrigatório", "O nome do tópico não pode estar vazio.");
            return;
        }

        if (max < min) {
            mostrarAlerta("Valores inválidos", "O máximo de aulas não pode ser menor que o mínimo.");
            return;
        }

        Topico topico = new Topico(nome, min, max, peso, disciplinaIdAtual, avaliacao, null);
        try {
            topicoDAO.salvar(topico);
        } catch (SQLException e) {
            mostrarAlerta("Erro no banco", "Não foi possível salvar o tópico: " + e.getMessage());
            return;
        }
        topicosCache.add(topico);
        topicosPendentes.add(topico);
        adicionarLinhaTopico(topico);
        atualizarIndicadores();
        limparCampos();
        redistribuir();
        App.setAlteracaoNaoSalva(true);
    }

    @FXML
    private void clicarSalvar() {
        try {
            List<javafx.scene.Node> linhas = containerTopicos.getChildren();
            for (int i = 0; i < linhas.size(); i++) {
                HBox linha = (HBox) linhas.get(i);
                Label lblNome = (Label) linha.getChildren().get(1);
                String nome = lblNome.getText();
                final int ordem = i;
                topicosCache.stream()
                        .filter(t -> t.getNome().equals(nome))
                        .findFirst()
                        .ifPresent(t -> t.setOrdem(ordem));
            }

            if (!topicosCache.isEmpty()) {
                topicoDAO.atualizarOrdens(topicosCache);
            }

            aulaDAO.clearTopicoByDisciplina(disciplinaIdAtual);
            aulaDAO.salvarDistribuicao(aulasCronograma);

            mostrarAlerta("Sucesso", "Planejamento salvo com sucesso!");
            App.setAlteracaoNaoSalva(false);
            topicosPendentes.clear();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao salvar", e.getMessage());
        }
    }

    private void descartarAlteracoes() {
        for (Topico topico : new ArrayList<>(topicosPendentes)) {
            try {
                if (topico.getId() != null) {
                    aulaDAO.clearTopicoById(topico.getId());
                    topicoDAO.deletar(topico.getId());
                }
            } catch (SQLException e) {
                mostrarAlerta("Erro ao descartar", "Não foi possível reverter o tópico: " + topico.getNome());
            }
        }
        topicosPendentes.clear();
    }

    private void adicionarLinhaTopico(Topico topico) {
        HBox linha = new HBox(10);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.getStyleClass().add("topico-linha");

        VBox setas = new VBox(2);
        setas.setAlignment(Pos.CENTER);
        setas.setPrefWidth(30);
        Button btnCima = new Button("▲");
        Button btnBaixo = new Button("▼");
        btnCima.getStyleClass().add("btn-seta");
        btnBaixo.getStyleClass().add("btn-seta");
        btnCima.setOnAction(e -> moverTopico(linha, -1));
        btnBaixo.setOnAction(e -> moverTopico(linha, +1));
        setas.getChildren().addAll(btnCima, btnBaixo);

        Label lblNome = new Label(topico.getNome());
        lblNome.getStyleClass().add("topico-nome");
        HBox.setHgrow(lblNome, Priority.ALWAYS);

        Label lblInfo = new Label("Min:" + topico.getMinAulas() + " Máx:" + topico.getMaxAulas() + " P:" + topico.getPeso());
        lblInfo.getStyleClass().add("topico-info");

        Label lblBadge = new Label(String.valueOf(topico.getMaxAulas()));
        lblBadge.getStyleClass().add("topico-badge");

        Region spacerInfo = new Region();
        HBox.setHgrow(spacerInfo, Priority.ALWAYS);

        Button btnDeletar = new Button("🗑");
        btnDeletar.getStyleClass().add("btn-deletar");
        btnDeletar.setOnAction(e -> {
            try {
                if (topico.getId() != null) {
                    aulasCronograma.stream()
                            .filter(a -> topico.getId().equals(a.getTopicoId()) && a.isAncorada())
                            .forEach(a -> {
                                a.setAncorada(false);
                                a.setTopicoId(null);
                            });
                    aulaDAO.clearTopicoById(topico.getId());
                    topicoDAO.deletar(topico.getId());
                } else {
                    topicosPendentes.remove(topico);
                }
                topicosCache.remove(topico);
                containerTopicos.getChildren().remove(linha);
                tabelaCronograma.refresh();
                atualizarIndicadores();
                redistribuir();
            } catch (SQLException ex) {
                mostrarAlerta("Erro ao deletar", "Não foi possível deletar o tópico: " + ex.getMessage());
            }
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

            List<String> ordemNomes = containerTopicos.getChildren().stream()
                    .map(n -> ((Label) ((HBox) n).getChildren().get(1)).getText())
                    .collect(Collectors.toList());

            topicosCache.sort(Comparator.comparingInt(t -> ordemNomes.indexOf(t.getNome())));

            redistribuir();
        }
    }

    private void redistribuir() {
        App.setAlteracaoNaoSalva(true);
        try {
            distribuicaoService.distribuir(aulasCronograma, topicosCache);
        } catch (IllegalStateException e) {
            mostrarAlerta("Distribuição impossível", e.getMessage());
        }
        tabelaCronograma.getItems().setAll(aulasCronograma);
        atualizarIndicadores();
    }


    private void atualizarIndicadores() {
        List<Aula> aulas = tabelaCronograma.getItems();
        int totais = aulas.size();
        int planejadas = (int) aulas.stream()
                .filter(a -> a.getTopicoId() != null)
                .count();

        lblAulasTotais.setText(String.valueOf(totais));
        lblAulasPlanejadas.setText(String.valueOf(planejadas));
        lblAulasRestantes.setText(String.valueOf(totais - planejadas));
        lblTotalTopicos.setText(String.valueOf(containerTopicos.getChildren().size()));
        lblHoraPlanejada.setText(formatarHoras(planejadas * 50));
        lblHoraTotal.setText("/ " + formatarHoras(totais * 50));
    }

    private String formatarHoras(int totalMinutos) {
        return String.format("%02d:%02d", totalMinutos / 60, totalMinutos % 60);
    }

    private void limparCampos() {
        txtTopico.clear();
        spinnerMin.getValueFactory().setValue(1);
        spinnerMax.getValueFactory().setValue(1);
        cbPeso.setValue("Peso 1");
        chkAvaliacao.setSelected(false);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}