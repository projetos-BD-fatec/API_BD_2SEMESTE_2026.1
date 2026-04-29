package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.Topico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TopicoDAO {

    public void salvar(Topico topico) throws SQLException {
        String sql = "INSERT INTO topicos (nome, min_aulas, max_aulas, peso, disciplina_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, topico.getNome());
            stmt.setInt(2, topico.getMinAulas());
            stmt.setInt(3, topico.getMaxAulas());
            stmt.setInt(4, topico.getPeso());
            stmt.setLong(5, topico.getDisciplinaId());

            stmt.executeUpdate();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM topicos WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
