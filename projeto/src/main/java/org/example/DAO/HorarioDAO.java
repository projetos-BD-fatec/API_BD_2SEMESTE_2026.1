package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.DiaSemana;
import org.example.model.Horario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {
    public List<Horario> findByDisciplinaID(Long disciplinaId) {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT * FROM horario WHERE disciplina_id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, disciplinaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String diaSemanaStr = rs.getString("dia_semana");
                DiaSemana diaSemana = DiaSemana.fromBanco(diaSemanaStr);
                Horario horario = new Horario(
                        rs.getLong("id"),
                        rs.getLong("disciplina_id"),
                        diaSemana,
                        rs.getObject("hora_inicio", LocalTime.class),
                        rs.getObject("hora_fim", LocalTime.class)
                );
                horarios.add(horario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar os horários", e);
        }
        return horarios;
    }
}
