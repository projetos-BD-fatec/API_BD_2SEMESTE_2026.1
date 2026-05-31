package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.App;
import org.example.DAO.DisciplinaDAO;
import org.example.DAO.UsuarioDAO;
import org.example.model.*;
import org.example.service.CalendarioService;
import org.example.util.DadosFixos;
import org.example.util.DropdownMenu;
import org.example.util.UserSession;
import org.example.DAO.AulaDAO;
import org.example.DAO.CalendarioDAO;
import org.example.DAO.HorarioDAO;
import org.example.service.AulaService;
import org.example.service.DisciplinaService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.concurrent.Task;
import javafx.application.Platform;
import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class DisciplinasController {

    @FXML private ComboBox<Turno> cbPeriodo;
    @FXML private ComboBox<DiaSemana> cbDia;
    @FXML private ComboBox<LocalTime> cbInicio;
    @FXML private ComboBox<LocalTime> cbFim;
    @FXML private TableView<Horario> tabelaHorarios;
    @FXML private TableColumn<Horario, String> colDia;
    @FXML private TableColumn<Horario, String> colInicio;
    @FXML private TableColumn<Horario, String> colFim;
    @FXML private TableColumn<Horario, Void> colExcluir;
    @FXML private ComboBox<Integer> cbCargaHoraria;
    @FXML private ComboBox<String> cbCurso;
    @FXML private ComboBox<String> cbSemestre;
    @FXML private Button btnCalendario;
    @FXML private Label labelUsuario;
    @FXML private javafx.scene.layout.TilePane containerDisciplinas;
    @FXML private TextField tfNomeDisciplina;

    private Popup dropdown;
    private final ObservableList<Horario> listaHorarios = FXCollections.observableArrayList();
    private final Image iconeFolder = new Image(
            getClass().getResource("/static/imagens/folder.png").toExternalForm()
    );
    private Usuario usuario = UserSession.getInstance().getUsuarioLogado();
    private CalendarioService calendarioService = new CalendarioService();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private DisciplinaService disciplinaService = new DisciplinaService();

    @FXML
    void salvarDisciplina() {
        String nome = tfNomeDisciplina.getText();
        String curso = cbCurso.getValue();
        String semestreStr = cbSemestre.getValue();
        Integer cargaHoraria = cbCargaHoraria.getValue();

        if (nome == null || nome.isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING,"Aviso", null, "Preencha o nome da disciplina.");
            return;
        }
        if (curso == null || semestreStr == null || cargaHoraria == null) {
            mostrarAlerta(Alert.AlertType.WARNING,"Aviso", null, "Preencha todos os campos (curso, semestre e carga horária).");
            return;
        }
        if (listaHorarios.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,"Aviso", null, "Adicione ao menos um horário.");
            return;
        }

        Integer semestre = Integer.parseInt(semestreStr.replace("º Sem", "").trim());
        Disciplina disciplina = new Disciplina(null, nome, cargaHoraria, curso, semestre);

        Stage loading = criarModalCarregando("Criando disciplina...");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
            Long disciplinaId = new DisciplinaService().salvar(disciplina, new ArrayList<>(listaHorarios), UserSession.getInstance());
            new AulaService(new HorarioDAO(), new CalendarioDAO(), new AulaDAO()).gerarAulas(disciplinaId, cargaHoraria);
            return null;
            }
        };

        task.setOnRunning(e -> {
            loading.show();
        });

        task.setOnSucceeded(e -> {
            loading.close();
            try {
                App.navegarParaDisciplinas();
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR,"Erro", null, ex.getMessage());
            }
        });

        task.setOnFailed(e -> {
            loading.close();
            Throwable erro = task.getException();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar");
            alert.setHeaderText(null);
            alert.setContentText("Erro: " + erro.getMessage());
            alert.showAndWait();
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    @FXML
    public void initialize() {
        Usuario usuario = UserSession.getInstance().getUsuarioLogado();
        labelUsuario.setText("\uD83D\uDC64" + usuario.getNome());
        btnCalendario.setText(usuario.getPeriodoAtual());
        Long usuarioId = UserSession.getInstance().getUsuarioLogado().getId();
        String usuarioPeriodo = UserSession.getInstance().getUsuarioLogado().getPeriodoAtual();
        List<Disciplina> disciplinas = new DisciplinaDAO().findByUsuarioId(usuarioId, usuarioPeriodo);

        for (Disciplina disciplina : disciplinas) {
            StackPane card = criarCard(disciplina);
            containerDisciplinas.getChildren().add(card);
        }
        cbDia.getItems().setAll(DiaSemana.values());

        cbPeriodo.getItems().setAll(Turno.values());
        cbPeriodo.setPromptText("Período");

        cbDia.setDisable(true);
        cbInicio.setDisable(true);
        cbFim.setDisable(true);

        cbInicio.getItems().setAll(DadosFixos.HORARIOS_NOITE);
        cbFim.getItems().setAll(DadosFixos.HORARIOS_NOITE);

        cbPeriodo.valueProperty().addListener((obs, antigo, novo) -> {
            ObservableList<LocalTime> horarios = novo == Turno.MANHA
                    ? DadosFixos.HORARIOS_MANHA
                    : DadosFixos.HORARIOS_NOITE;

            cbInicio.setItems(horarios);
            cbFim.setItems(horarios);
            cbInicio.setValue(null);
            cbFim.setValue(null);

            cbDia.setDisable(false);
            cbInicio.setDisable(false);
            cbFim.setDisable(false);
        });

        cbInicio.valueProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                ObservableList<LocalTime> turnoAtual = cbPeriodo.getValue() == Turno.MANHA
                        ? DadosFixos.HORARIOS_MANHA
                        : DadosFixos.HORARIOS_NOITE;

                cbFim.setItems(FXCollections.observableArrayList(
                        turnoAtual.filtered(h -> h.isAfter(novo))
                ));

                if (cbFim.getValue() != null && !cbFim.getValue().isAfter(novo)) {
                    cbFim.setValue(null);
                }
            }
        });

        colDia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDiaSemana().getValorBanco()));
        colInicio.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getHoraInicio().toString()));
        colFim.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getHoraFim().toString()));
        colExcluir.setCellFactory(col -> new TableCell<>() {
            private final Button btnExcluir = new Button("x");
            {
                btnExcluir.setOnAction(e ->{
                    Horario horario = getTableView().getItems().get(getIndex());
                    listaHorarios.remove(horario);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnExcluir);
            }
        });

        tabelaHorarios.setItems(listaHorarios);

        cbCurso.getItems().setAll(DadosFixos.CURSOS);
        cbSemestre.getItems().setAll(DadosFixos.SEMESTRES);
        cbCargaHoraria.getItems().setAll(DadosFixos.CARGAS_HORARIAS);

        cbCurso.setEditable(false);
        cbSemestre.setEditable(false);
        cbCargaHoraria.setEditable(false);

        dropdown = DropdownMenu.criar(btnCalendario, new String[][]{
                {"📅 Resumo Semestre Atual", "atual"},
                {"🎓 Iniciar Novo Semestre", "novo"}
        }, this::tratarItemDropdown);
    }

    @FXML void clicarSair() {
        usuario = null;
        try {
            App.sairParaLogin();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void clicarBtnAdicionar() {
        DiaSemana dia = cbDia.getValue();
        LocalTime inicio = cbInicio.getValue();
        LocalTime fim = cbFim.getValue();

        if (dia == null || inicio == null || fim == null) return;
        if (!fim.isAfter(inicio)) return;

        boolean diaJaCadastrado = listaHorarios.stream()
                .filter(h ->h.getDiaSemana().equals(dia))
                        .anyMatch(h -> inicio.isBefore(h.getHoraFim()) && fim.isAfter(h.getHoraInicio()));
        if (diaJaCadastrado) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dia duplicado");
            alert.setHeaderText(null);
            alert.setContentText(" Já existe um horário cadastrado para " + dia.getValorBanco() + ".");
            alert.showAndWait();
            return;
        }

        Horario horario = new Horario(null, null, dia, inicio, fim);
        listaHorarios.add(horario);
    }

    private void tratarItemDropdown(String id) {
        if ("atual".equals(id)) {
            String periodoAtual = usuario.getPeriodoAtual();
            String[] partes = periodoAtual.split("\\.");
            int ano = Integer.parseInt(partes[0]);
            int semestre = Integer.parseInt(partes[1]);
            try {
                ResumoPeriodo resumo = calendarioService.buscarResumo(ano, semestre);
                if (resumo == null) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Indisponível", null,"Nenhuma informação encontrada para este semestre.");
                    return;
                }
                abrirModalResumo(resumo);
            } catch (IOException ex) {
                mostrarAlerta(Alert.AlertType.ERROR,"Erro", null, "Não foi possível buscar o calendário.");
            }
        } else if ("novo".equals(id)) {
            abrirModalNovo();
        }
    }

    private void abrirModalResumo(ResumoPeriodo resumo) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(btnCalendario.getScene().getWindow());
        modal.setTitle("Resumo do semestre");
        modal.setResizable(false);

        Label titulo = new Label("Resumo das datas do semestre atual:");
        titulo.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2C2C2C;"
        );

        VBox lista = new VBox(8);
        lista.getChildren().addAll(
                criarLabel("Início das aulas: " + formatarData(resumo.getInicioAulas()), "#16344B"),
                criarLabel("Kickoff: " + formatarData(resumo.getKickoff().getInicio()) + " a " + formatarData(resumo.getKickoff().getFim()), "#755C36"),
                criarLabel("Planning: " + formatarData(resumo.getPlanning().getInicio()) + " a " + formatarData(resumo.getPlanning().getFim()), "#B5A081"),
                criarLabel("Sprint 1: " + formatarData(resumo.getSprint1().getInicio()) + " a " + formatarData(resumo.getSprint1().getFim()), "#6486A0"),
                criarLabel("Review/Planning 1: " + formatarData(resumo.getReviewPlanning1().getInicio()) + " a " + formatarData(resumo.getReviewPlanning1().getFim()), "#B5A081"),
                criarLabel("Sprint 2: " + formatarData(resumo.getSprint2().getInicio()) + " a " + formatarData(resumo.getSprint2().getFim()), "#6486A0"),
                criarLabel("Review/Planning 2: " + formatarData(resumo.getReviewPlanning2().getInicio()) + " a " + formatarData(resumo.getReviewPlanning2().getFim()), "#B5A081"),
                criarLabel("Sprint 3: " + formatarData(resumo.getSprint3().getInicio()) + " a " + formatarData(resumo.getSprint3().getFim()), "#6486A0"),
                criarLabel("Review Final: " + formatarData(resumo.getReview().getInicio()) + " a " + formatarData(resumo.getReview().getFim()), "#B5A081")
        );

        if (resumo.getFeira() != null) {
            lista.getChildren().add(criarLabel("Feira de Soluções: " + formatarData(resumo.getFeira()), "#755C36"));
        }

        lista.getChildren().add(
                criarLabel(
                        "Fim das aulas: " + formatarData(resumo.getFimAulas()),
                        "#16344B"
                )
        );

        Button btnFechar = new Button("Fechar");

        btnFechar.setOnAction(e -> modal.close());
        btnFechar.setStyle("-fx-background-color: #F25958; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand");

        HBox rodape = new HBox(10, btnFechar);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(20, titulo, lista, rodape);
        layout.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-min-width: 500;");

        modal.setScene(new Scene(layout));
        modal.show();
    }

    private Label criarLabel(String texto, String corFundo) {
        Label label = new Label(texto);

        label.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: " + corFundo + ";" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 8 12;"
        );

        label.setMaxWidth(Double.MAX_VALUE);

        return label;
    }

    private void abrirModalNovo() {
        String periodoAtual = usuario.getPeriodoAtual();
        String[] partes = periodoAtual.split("\\.");
        int ano = Integer.parseInt(partes[0]);
        int semestre = Integer.parseInt(partes[1]);
        int proximoAno = semestre == 1 ? ano : ano + 1;
        int proximoSemestre = semestre == 1 ? 2 : 1;
        String proximoPeriodo = proximoAno + "." + proximoSemestre;

        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(btnCalendario.getScene().getWindow());
        modal.setTitle("Iniciar Novo Semestre");
        modal.setResizable(false);

        Label lblAtual = new Label("Semestre atual: " + periodoAtual);
        Label lblProximo = new Label("Próximo semestre: " + proximoPeriodo);

        Button btnConfirmar = new Button("Iniciar semestre " + proximoPeriodo);
        btnConfirmar.setStyle(" -fx-background-color: #6D9D7B; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(" -fx-background-color: #F25958; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand");

        btnCancelar.setOnAction(e -> modal.close());

        btnConfirmar.setOnAction(e -> {
            try {
                ResumoPeriodo resumo = calendarioService.buscarResumo(proximoAno, proximoSemestre);
                if (resumo == null) {
                    mostrarAlerta(Alert.AlertType.WARNING,"Indisponível", null, "Nenhuma informação encontrada para este semestre. Tente novamente mais tarde.");
                    return;
                }
                modal.close();
                abrirModalConfirmar(resumo);
            } catch (IOException ex) {
                mostrarAlerta(Alert.AlertType.ERROR,"Erro", null,"Não foi possível buscar o calendário.");
            }
        });

        HBox rodape = new HBox(10, btnCancelar, btnConfirmar);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(20, lblAtual, lblProximo, rodape);
        layout.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-min-width: 400;");

        modal.setScene(new Scene(layout));
        modal.show();
    }

    private void abrirModalConfirmar(ResumoPeriodo resumo) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(btnCalendario.getScene().getWindow());
        modal.setTitle("Confirmar Semestre");
        modal.setResizable(false);

        Label titulo = new Label("Verifique todas as datas antes de confirmar:");
        titulo.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2C2C2C;"
        );

        VBox lista = new VBox(8);
        lista.getChildren().addAll(
                criarLabel("Início das aulas: " + formatarData(resumo.getInicioAulas()), "#16344B"),
                criarLabel("Kickoff: " + formatarData(resumo.getKickoff().getInicio()) + " a " + formatarData(resumo.getKickoff().getFim()), "#755C36"),
                criarLabel("Planning: " + formatarData(resumo.getPlanning().getInicio()) + " a " + formatarData(resumo.getPlanning().getFim()), "#B5A081"),
                criarLabel("Sprint 1: " + formatarData(resumo.getSprint1().getInicio()) + " a " + formatarData(resumo.getSprint1().getFim()), "#6486A0"),
                criarLabel("Review/Planning 1: " + formatarData(resumo.getReviewPlanning1().getInicio()) + " a " + formatarData(resumo.getReviewPlanning1().getFim()), "#B5A081"),
                criarLabel("Sprint 2: " + formatarData(resumo.getSprint2().getInicio()) + " a " + formatarData(resumo.getSprint2().getFim()), "#6486A0"),
                criarLabel("Review/Planning 2: " + formatarData(resumo.getReviewPlanning2().getInicio()) + " a " + formatarData(resumo.getReviewPlanning2().getFim()), "#B5A081"),
                criarLabel("Sprint 3: " + formatarData(resumo.getSprint3().getInicio()) + " a " + formatarData(resumo.getSprint3().getFim()), "#6486A0"),
                criarLabel("Review Final: " + formatarData(resumo.getReview().getInicio()) + " a " + formatarData(resumo.getReview().getFim()), "#B5A081")
        );

        if (resumo.getFeira() != null) {
            lista.getChildren().add(criarLabel("Feira de Soluções: " + formatarData(resumo.getFeira()), "#755C36"));
        }

        lista.getChildren().add(
                criarLabel(
                        "Fim das aulas: " + formatarData(resumo.getFimAulas()),
                        "#16344B"
                )
        );

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle(" -fx-background-color: #6D9D7B; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(" -fx-background-color: #F25958; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand");

        btnCancelar.setOnAction(e -> modal.close());

        btnConfirmar.setOnAction(e -> {
            if (!calendarioService.periodoJaExiste(resumo.getPeriodo())) {
                calendarioService.iniciarSemestre(resumo);
            }
            disciplinaService.deletarDisciplinas(usuario);
            usuarioDAO.atualizarPeriodo(usuario.getId(), resumo.getPeriodo());
            usuario.setPeriodoAtual(resumo.getPeriodo());
            modal.close();
            mostrarAlerta(Alert.AlertType.INFORMATION,"Sucesso", null,"Semestre " + resumo.getPeriodo() + " iniciado com sucesso!");
        });

        HBox rodape = new HBox(10, btnCancelar, btnConfirmar);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(20, titulo, lista, rodape);
        layout.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-min-width: 400;");

        modal.setScene(new Scene(layout));
        modal.show();
    }

    @FXML
    private void abrirMenu() {
        DropdownMenu.alternarVisibilidade(dropdown, btnCalendario);
    }

    private String formatarData(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String cabecalho, String mensagem) {

        javafx.stage.Stage modal = new javafx.stage.Stage();
        modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);
        modal.setResizable(false);

        String corBotao = "#F25958";
        String icone = "⚠";

        if (tipo == Alert.AlertType.ERROR) {
            corBotao = "#C62828";
            icone = "✖";
        } else if (tipo == Alert.AlertType.INFORMATION) {
            corBotao = "#6D9D7B";
            icone = "✓";
        }

        javafx.scene.control.Label lblTitulo = new javafx.scene.control.Label(icone + " " + titulo);
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER_LEFT);
        lblTitulo.setStyle("-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #000000;" +
                "-fx-background-color: #F5E6CF;" +
                "-fx-border-color: #000000;" +
                "-fx-border-width: 0 0 2 0;" +
                "-fx-padding: 12 18;" +
                "-fx-background-radius: 8 8 0 0;"
        );

        VBox corpo = new VBox();
        if (cabecalho != null && !cabecalho.isBlank()) {
            Label lblCabecalho = new Label(cabecalho);
            lblCabecalho.setStyle(
                    "-fx-font-size: 15px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 12 18 0 18;"
            );
            corpo.getChildren().add(lblCabecalho);
        }

        Label lblMensagem = new Label(mensagem);
        lblMensagem.setWrapText(true);
        lblMensagem.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-padding: 12 18 18 18;"
        );
        corpo.getChildren().add(lblMensagem);
        Button btnOk = new Button("OK");
        btnOk.setStyle(
                "-fx-background-color: " +
                        corBotao +
                        ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #000000;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-padding: 8 22;" +
                        "-fx-cursor: hand;"
        );
        btnOk.setOnAction(e -> modal.close());

        HBox rodape = new HBox(btnOk);

        rodape.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        rodape.setPadding(new javafx.geometry.Insets(0, 18, 18, 18));
        VBox layout = new VBox(lblTitulo, corpo, rodape);
        layout.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2 5 5 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;"
        );
        modal.setScene(new javafx.scene.Scene(layout));
        modal.showAndWait();
    }

    private Stage criarModalCarregando(String mensagem) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(tfNomeDisciplina.getScene().getWindow());
        modal.setResizable(false);
        ProgressIndicator loading = new ProgressIndicator();
        Label texto = new Label(mensagem);

        texto.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;"
        );
        VBox layout = new VBox(15, loading, texto);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;"
        );
        modal.setScene(new Scene(layout));
        return modal;
    }

    private StackPane criarCard(Disciplina disciplina) {
        Label lblNome = new Label(disciplina.getNome());
        lblNome.setPrefHeight(USE_COMPUTED_SIZE);
        lblNome.setPrefWidth(312);
        lblNome.getStyleClass().add("cardNomeDisciplina");
        lblNome.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblNome.setWrapText(true);
        lblNome.setFont(javafx.scene.text.Font.font("System Bold", 21));

        String subtitulo = disciplina.getCurso() + " - " + disciplina.getSemestre() + "º Sem";
        Label lblSubtitulo = new Label(subtitulo);
        lblSubtitulo.setWrapText(true);
        lblSubtitulo.setMaxWidth(280);
        lblSubtitulo.setPrefWidth(USE_COMPUTED_SIZE);
        lblSubtitulo.getStyleClass().add("cardSubtituloDisciplina");
        lblSubtitulo.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblSubtitulo.setFont(javafx.scene.text.Font.font(23));

        VBox cardBg = new VBox(lblNome, lblSubtitulo);
        cardBg.setAlignment(javafx.geometry.Pos.CENTER);
        cardBg.setPrefWidth(333);
        cardBg.setMaxWidth(Double.MAX_VALUE);
        cardBg.setMaxHeight(Double.MAX_VALUE);
        cardBg.getStyleClass().add("cardBgTitulo");

        ImageView icone = new ImageView(iconeFolder);
        icone.setFitHeight(157);
        icone.setFitWidth(90);
        icone.setPreserveRatio(true);

        VBox iconeContainer = new VBox(icone);
        iconeContainer.setAlignment(javafx.geometry.Pos.CENTER);
        iconeContainer.setPrefHeight(130);
        iconeContainer.setPadding(
                new javafx.geometry.Insets(12, 0, 12, 0)
        );
        iconeContainer.setPrefWidth(293);

        VBox conteudo = new VBox(iconeContainer, cardBg);
        conteudo.setPrefWidth(303);
        conteudo.setPrefHeight(250);
        VBox.setVgrow(cardBg, javafx.scene.layout.Priority.ALWAYS);

        Button btnDeletar = new Button("X");
        btnDeletar.setStyle(
                "-fx-background-color: #F25958;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-alignment: center;" +
                        "-fx-padding: 0;" +
                        "-fx-min-width: 20px;" +
                        "-fx-min-height: 20px;" +
                        "-fx-max-width: 20px;" +
                        "-fx-max-height: 20px;" +
                        "-fx-cursor: hand;"
        );

        StackPane card = new StackPane();
        card.getChildren().addAll(conteudo, btnDeletar);

        card.getStyleClass().add("cardDisciplina");
        card.setPrefSize(250, 250);
        StackPane.setAlignment(btnDeletar, Pos.TOP_RIGHT);

        StackPane.setMargin(btnDeletar, new Insets(6, 6, 0, 0));

        card.setOnMouseClicked(e -> {
            try {
                App.navegarParaPlanejamento(disciplina.getId());
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR,"Erro", null,"Não foi possível abrir.");
            }
        });

        btnDeletar.setOnAction(e -> {
            e.consume();

            try {
                disciplinaDAO.deletarDisciplina(disciplina.getId());
                containerDisciplinas.getChildren().remove(card);
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR,"Erro", null,"Não foi possível excluir.");
            }
        });
        return card;
    }
}
