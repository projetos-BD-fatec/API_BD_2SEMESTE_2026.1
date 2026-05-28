package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.Topico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicoDAO {
    public List<Topico> findByDisciplinaId(Long disciplinaId) {
        List<Topico> topicos = new ArrayList<>();
        String sql = "SELECT * FROM topico WHERE disciplina_id = ? ORDER BY ordem";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, disciplinaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Topico topico = new Topico(
                        rs.getLong("id"),
                        rs.getObject("nome", String.class),
                        rs.getObject("min_aulas", Integer.class),
                        rs.getObject("max_aulas", Integer.class),
                        rs.getObject("peso", Integer.class),
                        rs.getLong("disciplina_id"),
                        rs.getBoolean("avaliacao"),
                        rs.getObject("ordem", Integer.class)
                );
                topicos.add(topico);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar topicos", e);
        }
        return topicos;
    }

    public void salvar(Topico topico) throws SQLException {
        String sql = "INSERT INTO topico (nome, min_aulas, max_aulas, peso, disciplina_id, avaliacao, ordem) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, topico.getNome());
            stmt.setInt(2, topico.getMinAulas());
            stmt.setInt(3, topico.getMaxAulas());
            stmt.setInt(4, topico.getPeso());
            stmt.setLong(5, topico.getDisciplinaId());
            stmt.setBoolean(6, topico.getAvaliacao());
            stmt.setObject(7, topico.getOrdem());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                topico.setId(rs.getLong(1));
            }
        }
    }

    public void atualizarOrdens(List<Topico> topicos) throws SQLException {
        String sql = "UPDATE topico SET ordem = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Topico t : topicos) {
                stmt.setObject(1, t.getOrdem());
                stmt.setLong(2, t.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public void deletar(Long id) throws SQLException {
        String sql = "DELETE FROM topico WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
