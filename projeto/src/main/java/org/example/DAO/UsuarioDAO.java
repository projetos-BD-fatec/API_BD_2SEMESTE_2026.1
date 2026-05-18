package org.example.DAO;

import org.example.model.Usuario;
import org.example.infrastructure.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

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