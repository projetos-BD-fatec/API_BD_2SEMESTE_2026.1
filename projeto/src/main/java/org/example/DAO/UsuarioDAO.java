package org.example.DAO;

import org.example.model.Usuario;
import org.example.infrastructure.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean cadastrar(Usuario usuario) {

        String sqlVerificacao = "SELECT id FROM usuario WHERE email = ? OR cpf = ?";
        String sqlInsert = "INSERT INTO usuario (nome, email, cpf, periodo_atual) VALUES (?, ?, ?, ?)";
        String sqlSemestre = "SELECT periodo FROM semestre ORDER BY data_inicio DESC LIMIT 1";

        try (Connection conn = ConexaoBD.conectar()) {
            if (conn == null) return false;

            try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerificacao)) {
                stmtVerifica.setString(1, usuario.getEmail());
                stmtVerifica.setString(2, usuario.getCpf());
                try (ResultSet rs = stmtVerifica.executeQuery()) {
                    if (rs.next()) return false;
                }
            }

            String periodoAtual = null;
            try (PreparedStatement stmtSemestre = conn.prepareStatement(sqlSemestre);
                ResultSet rs = stmtSemestre.executeQuery()) {
                if (rs.next()) {
                    periodoAtual = rs.getString("periodo");
                }
            }
            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, usuario.getNome());
                stmtInsert.setString(2, usuario.getEmail());
                stmtInsert.setString(3, usuario.getCpf());
                stmtInsert.setString(4, periodoAtual);
                stmtInsert.executeUpdate();
                return true;
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
                        rs.getString("cpf"),
                        rs.getString("periodo_atual")
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

    public void atualizarPeriodo(Long usuarioId, String periodo) {
        String sql = "UPDATE usuario SET periodo_atual = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, periodo);
            stmt.setLong(2, usuarioId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar período do usuário", e);
        }
    }
}