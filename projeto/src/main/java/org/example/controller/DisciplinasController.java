package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
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

    @FXML
    void salvarDisciplina() {
        String nome = tfNomeDisciplina.getText();
        String curso = cbCurso.getValue();
        String semestreStr = cbSemestre.getValue();
        Integer cargaHoraria = cbCargaHoraria.getValue();

        if (nome == null || nome.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Preencha o nome da disciplina.").showAndWait();
            return;
        }
        if (curso == null || semestreStr == null || cargaHoraria == null) {
            new Alert(Alert.AlertType.WARNING, "Preencha todos os campos (curso, semestre e carga horária).").showAndWait();
            return;
        }
        if (listaHorarios.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Adicione ao menos um horário.").showAndWait();
            return;
        }

        Integer semestre = Integer.parseInt(semestreStr.replace("º Sem", "").trim());
        Disciplina disciplina = new Disciplina(null, nome, cargaHoraria, curso, semestre);

        try {
            Long disciplinaId = new DisciplinaService()
                    .salvar(disciplina, new ArrayList<>(listaHorarios), UserSession.getInstance());
            new AulaService(new HorarioDAO(), new CalendarioDAO(), new AulaDAO())
                    .gerarAulas(disciplinaId, cargaHoraria);
            App.navegarParaDisciplinas();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar");
            alert.setHeaderText(null);
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void initialize() {
        Usuario usuario = UserSession.getInstance().getUsuarioLogado();
        labelUsuario.setText(usuario.getNome());
        btnCalendario.setText(usuario.getPeriodoAtual());
        Long usuarioId = UserSession.getInstance().getUsuarioLogado().getId();
        String usuarioPeriodo = UserSession.getInstance().getUsuarioLogado().getPeriodoAtual();
        List<Disciplina> disciplinas = new DisciplinaDAO().findByUsuarioId(usuarioId, usuarioPeriodo);

        for (Disciplina disciplina : disciplinas) {
            Button card = criarCard(disciplina);
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
                    mostrarAlerta("Indisponível", "Nenhuma informação encontrada para este semestre.");
                    return;
                }
                abrirModalResumo(resumo);
            } catch (IOException ex) {
                mostrarAlerta("Erro", "Não foi possível buscar o calendário.");
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
                criarLabel("Início das aulas: " + formatarData(resumo.getInicioAulas()), "#DCCFB8"),
                criarLabel("Kickoff: " + formatarData(resumo.getKickoff().getInicio()) + " a " + formatarData(resumo.getKickoff().getFim()), "#C9B99D"),
                criarLabel("Planning: " + formatarData(resumo.getPlanning().getInicio()) + " a " + formatarData(resumo.getPlanning().getFim()), "#E8D7B7"),
                criarLabel("Sprint 1: " + formatarData(resumo.getSprint1().getInicio()) + " a " + formatarData(resumo.getSprint1().getFim()), "#B8CBB8"),
                criarLabel("Review/Planning 1: " + formatarData(resumo.getReviewPlanning1().getInicio()) + " a " + formatarData(resumo.getReviewPlanning1().getFim()), "#E8D7B7"),
                criarLabel("Sprint 2: " + formatarData(resumo.getSprint2().getInicio()) + " a " + formatarData(resumo.getSprint2().getFim()), "#B8CBB8"),
                criarLabel("Review/Planning 2: " + formatarData(resumo.getReviewPlanning2().getInicio()) + " a " + formatarData(resumo.getReviewPlanning2().getFim()), "#E8D7B7"),
                criarLabel("Sprint 3: " + formatarData(resumo.getSprint3().getInicio()) + " a " + formatarData(resumo.getSprint3().getFim()), "#B8CBB8"),
                criarLabel("Review Final: " + formatarData(resumo.getReview().getInicio()) + " a " + formatarData(resumo.getReview().getFim()), "#E8D7B7")
        );

        if (resumo.getFeira() != null) {
            lista.getChildren().add(criarLabel("Feira de Soluções: " + formatarData(resumo.getFeira()), "#C9B99D"));
        }

        lista.getChildren().add(
                criarLabel(
                        "Fim das aulas: " + formatarData(resumo.getFimAulas()),
                        "#DCCFB8"
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
                    mostrarAlerta("Indisponível", "Nenhuma informação encontrada para este semestre. Tente novamente mais tarde.");
                    return;
                }
                modal.close();
                abrirModalConfirmar(resumo);
            } catch (IOException ex) {
                mostrarAlerta("Erro", "Não foi possível buscar o calendário.");
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
                criarLabel("Início das aulas: " + formatarData(resumo.getInicioAulas()), "#2C2C2C"),
                criarLabel("Kickoff: " + formatarData(resumo.getKickoff().getInicio()) + " a " + formatarData(resumo.getKickoff().getFim()), "#666666"),
                criarLabel("Planning: " + formatarData(resumo.getPlanning().getInicio()) + " a " + formatarData(resumo.getPlanning().getFim()), "#E6AF8E"),
                criarLabel("Sprint 1: " + formatarData(resumo.getSprint1().getInicio()) + " a " + formatarData(resumo.getSprint1().getFim()), "#8ECAE6"),
                criarLabel("Review/Planning 1: " + formatarData(resumo.getReviewPlanning1().getInicio()) + " a " + formatarData(resumo.getReviewPlanning1().getFim()), "#E6C98E"),
                criarLabel("Sprint 2: " + formatarData(resumo.getSprint2().getInicio()) + " a " + formatarData(resumo.getSprint2().getFim()), "#8ECAE6"),
                criarLabel("Review/Planning 2: " + formatarData(resumo.getReviewPlanning2().getInicio()) + " a " + formatarData(resumo.getReviewPlanning2().getFim()), "#E6C98E"),
                criarLabel("Sprint 3: " + formatarData(resumo.getSprint3().getInicio()) + " a " + formatarData(resumo.getSprint3().getFim()), "#8ECAE6"),
                criarLabel("Review Final: " + formatarData(resumo.getReview().getInicio()) + " a " + formatarData(resumo.getReview().getFim()), "#E6C98E")
        );

        if (resumo.getFeira() != null) {
            lista.getChildren().add(criarLabel("Feira de Soluções: " + formatarData(resumo.getFeira()), "#455C66"));
        }

        lista.getChildren().add(
                criarLabel(
                        "Fim das aulas: " + formatarData(resumo.getFimAulas()),
                        "#2C2C2C"
                )
        );

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle(" -fx-background-color: #6D9D7B; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(" -fx-background-color: #F25958; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand");

        btnCancelar.setOnAction(e -> modal.close());

        btnConfirmar.setOnAction(e -> {
            calendarioService.iniciarSemestre(resumo);
            usuarioDAO.atualizarPeriodo(usuario.getId(), resumo.getPeriodo());
            usuario.setPeriodoAtual(resumo.getPeriodo());
            modal.close();
            mostrarAlerta("Sucesso", "Semestre " + resumo.getPeriodo() + " iniciado com sucesso!");
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

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private Button criarCard(Disciplina disciplina) {
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

        Button card = new Button();
        card.getStyleClass().add("cardDisciplina");
        card.setGraphic(conteudo);
        card.setMinHeight(250);
        card.setMaxHeight(250);
        card.setMinWidth(250);
        card.setMaxWidth(250);
        card.setOnAction(e -> {
            try {
                App.navegarParaPlanejamento(disciplina.getId());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao abrir planejamento");
                alert.setHeaderText(null);
                alert.setContentText("Erro: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        return card;
    }
}
