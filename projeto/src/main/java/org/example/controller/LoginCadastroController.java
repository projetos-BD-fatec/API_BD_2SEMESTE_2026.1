package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.DAO.UsuarioDAO;
import org.example.model.Usuario;

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
            mostrarAlerta(Alert.AlertType.INFORMATION, "Login Aprovado", null, "Seja bem-vindo(a), " + usuarioLogado.getNome() + "!");
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

        private void mostrarAlerta (Alert.AlertType tipo, String titulo, String cabecalho, String mensagem){
            Alert alerta = new Alert(tipo);
            alerta.setTitle(titulo);
            alerta.setHeaderText(cabecalho);
            alerta.setContentText(mensagem);
            alerta.showAndWait();
        }
    }