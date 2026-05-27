package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.DAO.DisciplinaDAO;
import org.example.model.DiaSemana;
import org.example.model.Disciplina;
import org.example.model.Horario;
import org.example.model.Usuario;
import org.example.util.DadosFixos;
import org.example.util.DropdownMenu;
import org.example.util.Modal;
import org.example.util.UserSession;

import java.time.LocalTime;
import java.util.List;
import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class DisciplinasController {

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
    @FXML private HBox containerDisciplinas;

    private Popup dropdown;
    private final ObservableList<Horario> listaHorarios = FXCollections.observableArrayList();
    private final Image iconeFolder = new Image(
            getClass().getResource("/static/imagens/folder.png").toExternalForm()
    );


    @FXML
    public void initialize() {
        Usuario usuario = UserSession.getInstance().getUsuarioLogado();
        labelUsuario.setText(usuario.getNome());
        Long usuarioId = UserSession.getInstance().getUsuarioLogado().getId();
        List<Disciplina> disciplinas = new DisciplinaDAO().findByUsuarioId(usuarioId);

        for (Disciplina disciplina : disciplinas) {
            Button card = criarCard(disciplina);
            containerDisciplinas.getChildren().add(card);
        }
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

        colDia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDiaSemana().getValorBanco()));
        colInicio.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getHoraInicio().toString()));
        colFim.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getHoraFim().toString()));
        colExcluir.setCellFactory(col -> new TableCell<>(){
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
                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(btnExcluir);
                }
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
                {"📅 Alterar Semestre", "alterar"},
                {"🎓 Iniciar Novo Semestre", "novo"}
        }, this::tratarItemDropdown);
    }

    @FXML
    private void clicarBtnAdicionar() {
        DiaSemana dia = cbDia.getValue();
        LocalTime inicio = cbInicio.getValue();
        LocalTime fim = cbFim.getValue();

        if (dia == null || inicio == null || fim == null) return;
        if (!fim.isAfter(inicio)) return;

        boolean diaJaCadastrado = listaHorarios.stream()
                        .anyMatch(h -> h.getDiaSemana() == dia);
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

    @FXML
    private void clicarDisciplina() {
        try {
            App.navegarParaPlanejamento(1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void tratarItemDropdown(String id) {
        if ("alterar".equals(id)) {
            abrirModalAlterar();
        } else if ("novo".equals(id)) {
            abrirModalNovo();
        }
    }

    private void abrirModalAlterar() {
        Label conteudo = new Label("Selecione o semestre desejado...");
        Stage modal = Modal.criar(btnCalendario.getScene().getWindow(), "Alterar Semestre", conteudo);
        modal.show();
    }

    private void abrirModalNovo() {
        Label conteudo = new Label("Ao iniciar um novo semestre, o período atual será sobreposto e todas as disciplinas cadastradas serão deletadas, deseja continuar?");
        Stage modal = Modal.criar(btnCalendario.getScene().getWindow(), "Iniciar Novo Semestre", conteudo);
        modal.show();
    }

    @FXML
    private void abrirMenu() {
        DropdownMenu.alternarVisibilidade(dropdown, btnCalendario);
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
        cardBg.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        cardBg.setPrefWidth(333);
        cardBg.getStyleClass().add("cardBgTitulo");

        ImageView icone = new ImageView(iconeFolder);
        icone.setFitHeight(157);
        icone.setFitWidth(90);
        icone.setPreserveRatio(true);

        VBox iconeContainer = new VBox(icone);
        iconeContainer.setAlignment(javafx.geometry.Pos.CENTER);
        iconeContainer.setPrefHeight(USE_COMPUTED_SIZE);
        iconeContainer.setPrefWidth(293);

        VBox conteudo = new VBox(iconeContainer, cardBg);
        conteudo.setAlignment(javafx.geometry.Pos.CENTER);
        conteudo.setPrefHeight(USE_COMPUTED_SIZE);
        conteudo.setPrefWidth(303);

        Button card = new Button();
        card.setGraphic(conteudo);
        card.getStyleClass().add("cardDisciplina");
        card.setMinHeight(250);
        card.setMaxHeight(250);
        card.setMinWidth(250);
        card.setMaxWidth(250);
        card.setOnAction(e -> {
            try {
                App.navegarParaPlanejamento(disciplina.getId());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return card;
    }
}
