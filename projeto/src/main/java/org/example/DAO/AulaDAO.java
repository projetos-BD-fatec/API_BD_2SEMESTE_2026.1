package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.Aula;
import org.example.model.DiaSemana;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {

    public void salvar(Aula aula) {
        String sql = "INSERT INTO aula (disciplina_id, data, hora_inicio, hora_fim) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, aula.getDisciplinaId());
            stmt.setObject(2, aula.getData());
            stmt.setObject(3, aula.getHoraInicio());
            stmt.setObject(4, aula.getHoraFim());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aula", e);
        }
    }

    public List<Aula> findByDisciplinaId(Long disciplinaId) {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT a.*, c.dia_semana, c.evento FROM aula a JOIN calendario c ON a.data = c.data WHERE a.disciplina_id = ? ORDER BY  a.data, a.hora_inicio";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, disciplinaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String diaSemanaStr = rs.getString("dia_semana");
                DiaSemana diaSemana = DiaSemana.fromBanco(diaSemanaStr);
                Aula aula = new Aula(
                        rs.getLong("id"),
                        rs.getLong("disciplina_id"),
                        rs.getObject("data", LocalDate.class),
                        diaSemana,
                        rs.getObject("hora_inicio", LocalTime.class),
                        rs.getObject("hora_fim", LocalTime.class),
                        rs.getObject("evento", String.class),
                        rs.getObject("topico_id", Long.class)
                );
                aulas.add(aula);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as aulas", e);
        }
        return aulas;
    }

    public void updateTopicoId(Long aulaId, Long topicoId) throws SQLException {
        String sql = "UPDATE aula SET topico_id = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, topicoId);
            stmt.setObject(2, aulaId);
            stmt.executeUpdate();
        }
    }

    public void clearTopicoByDisciplina(Long disciplinaId) throws SQLException {
        String sql = "UPDATE aula SET topico_id = NULL WHERE disciplina_id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, disciplinaId);
            stmt.executeUpdate();
        }
    }

    public void clearTopicoById(Long topicoId) throws SQLException {
        String sql = "UPDATE aula SET topico_id = NULL WHERE topico_id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, topicoId);
            stmt.executeUpdate();
        }
    }

    public void salvarDistribuicao(List<Aula> aulas) throws SQLException {
        String sql = "UPDATE aula SET topico_id = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Aula aula : aulas) {
                stmt.setObject(1, aula.getTopicoId());
                stmt.setObject(2, aula.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}
