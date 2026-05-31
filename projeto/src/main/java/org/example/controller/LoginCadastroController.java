package org.example.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import org.example.App;
import org.example.DAO.UsuarioDAO;
import org.example.model.Usuario;
import org.example.util.UserSession;

public class LoginCadastroController {

    @FXML
    private TextField campoLoginEmail;
    @FXML
    private PasswordField campoLoginSenha;
    @FXML
    private Button btnEntrar;

    @FXML
    private TextField campoCadastroNome;
    @FXML
    private TextField campoCadastroEmail;
    @FXML
    private TextField campoCadastroSenha;
    @FXML
    private Button btnCadastrar;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void ligarLogin() {
        String email = campoLoginEmail.getText();
        String senha = campoLoginSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", null, "Por favor, preencha o E-mail e a Senha para entrar.");
            return;
        }

        System.out.println("Tentando autenticar o usuário: " + email + "...");
        Usuario usuarioLogado = usuarioDAO.login(email, senha);

        if (usuarioLogado != null) {
            UserSession.iniciarSessao(usuarioLogado);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Login Aprovado", null, "Seja bem-vindo(a), " + usuarioLogado.getNome() + "!");
            try {
            App.navegarParaDisciplinas();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Acesso Negado", null, "Usuário ou Senha incorretos. Tente novamente.");
            campoLoginSenha.clear();
        }
    }

        @FXML
        public void ligarCadastro () {
            String nome = campoCadastroNome.getText();
            String email = campoCadastroEmail.getText();
            String cpf = campoCadastroSenha.getText(); // O CPF está sendo usado no campo de senha

            // 1. Barreira de Campos Vazios
            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", null, "Por favor, preencha todos os campos.");
                return;
            }

            // 2. Barreira de Validação do E-mail
            if (!isEmailValido(email)) {
                mostrarAlerta(Alert.AlertType.WARNING, "E-mail Inválido", null, "Por favor, digite um e-mail no formato correto (exemplo@fatec.com).");
                return;
            }

            // 3. Barreira de Validação do CPF (Exatamente 11 dígitos numéricos)
            if (!isCpfValido(cpf)) {
                mostrarAlerta(Alert.AlertType.WARNING, "CPF Inválido", null, "O CPF deve conter exatamente 11 números.");
                return;
            }

            UsuarioDAO dao = new UsuarioDAO();

            Usuario novoUsuario = new Usuario(nome, email, cpf);

            boolean sucesso = dao.cadastrar(novoUsuario);

            if (sucesso) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cadastro Concluído", null, nome + " foi cadastrado com sucesso.");
                campoCadastroNome.clear();
                campoCadastroEmail.clear();
                campoCadastroSenha.clear();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro no Cadastro", null, "Não foi possível realizar o cadastro. E-mail ou CPF já cadastrados.");
            }
        }
        // Validador de E-mail
        private boolean isEmailValido (String email){
            String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            return email != null && email.matches(regexEmail);
        }

        // Validador de CPF
        private boolean isCpfValido (String cpf){
            if (cpf == null) return false;
            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            return cpfLimpo.length() == 11;
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
    }