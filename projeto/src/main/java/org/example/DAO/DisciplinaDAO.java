package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisciplinaDAO {
    public Disciplina findById(Long id) {
        String sql = "SELECT * FROM disciplina WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Disciplina(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getInt("carga_horaria"),
                            rs.getString("curso"),
                            rs.getInt("semestre")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a disciplina", e);
        }
        return null;
    }
}
