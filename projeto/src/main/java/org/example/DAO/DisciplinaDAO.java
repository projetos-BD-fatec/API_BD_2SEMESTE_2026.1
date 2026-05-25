package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public List<Disciplina> findByUsuarioId(Long usuarioId) {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT id, nome, carga_horaria, curso, semestre FROM disciplina WHERE usuario_id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Disciplina disciplina = new Disciplina(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getInt("carga_horaria"),
                        rs.getString("curso"),
                        rs.getInt("semestre")
                );
                disciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplinas do usuário", e);
        }

        return disciplinas;
    }
}