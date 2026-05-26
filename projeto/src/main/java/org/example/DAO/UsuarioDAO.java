package org.example.DAO;

import org.example.model.Usuario;
import org.example.infrastructure.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para cadastrar um novo usuário com verificação de duplicidade
    public boolean cadastrar(Usuario usuario) {
        // SQL 1: Verifica se já existe o email ou cpf
        String sqlVerificacao = "SELECT id FROM usuario WHERE email = ? OR cpf = ?";
        // SQL 2: Insere o novo usuário
        String sqlInsert = "INSERT INTO usuario (nome, email, cpf) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar()) {
            if (conn == null) {
                System.out.println("Erro: Falha na conexão com o banco.");
                return false;
            }

            // Checar duplicidade
            try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerificacao)) {
                stmtVerifica.setString(1, usuario.getEmail());
                stmtVerifica.setString(2, usuario.getCpf());

                try (ResultSet rs = stmtVerifica.executeQuery()) {
                    if (rs.next()) { // Se achou alguma linha, significa que já existe!
                        return false; // Retorna falso para avisar a tela que deu erro
                    }
                }
            }

            // Se não existe, prossegue com o cadastro
            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, usuario.getNome());
                stmtInsert.setString(2, usuario.getEmail());
                stmtInsert.setString(3, usuario.getCpf());

                stmtInsert.executeUpdate();
                return true; // Retorna verdadeiro indicando sucesso!
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public Usuario login(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND cpf = ?";
        Usuario usuarioLogado = null;

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuarioLogado = new Usuario(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf")
                );
                System.out.println("Login realizado com sucesso para: " + usuarioLogado.getNome() + "!");
            } else {
                System.out.println("E-mail ou CPF inválidos.");
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Erro ao realizar login no banco: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao tentar fazer login: " + e.getMessage());
        }

        return usuarioLogado;
    }
}