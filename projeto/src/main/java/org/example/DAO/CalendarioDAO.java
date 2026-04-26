package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.Calendario;
import org.example.model.DiaSemana;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarioDAO {
    public List<Calendario> findByDiaSemana(DiaSemana diaSemanaCD) {
        List<Calendario> diasCalendario = new ArrayList<>();
        String sql = "SELECT * FROM calendario WHERE dia_semana = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, diaSemanaCD.getValorBanco());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String diaSemanaStr = rs.getString("dia_semana");
                DiaSemana diaSemana = DiaSemana.fromBanco(diaSemanaStr);
                Calendario calendario = new Calendario(
                        rs.getObject("data", LocalDate.class),
                        diaSemana,
                        rs.getObject("evento", String.class)
                );
                diasCalendario.add(calendario);
                }
        } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar os dias do calendário", e);
            }
            return diasCalendario;
    }
}
