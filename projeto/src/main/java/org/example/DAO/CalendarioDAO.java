package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarioDAO {
    public List<Calendario> findByDiaSemana(DiaSemana diaSemanaCD, String periodo) {
        List<Calendario> diasCalendario = new ArrayList<>();
        String sql = "SELECT * FROM calendario WHERE dia_semana = ?::dia_semana AND periodo = ? ORDER BY data ASC";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, diaSemanaCD.getValorBanco());
            stmt.setString(2, periodo);
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

    public void salvarSemestre(ResumoPeriodo periodo) {
        String sql = "INSERT INTO semestre (periodo, data_inicio, data_fim) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, periodo.getPeriodo());
            stmt.setObject(2, periodo.getInicioAulas());
            stmt.setObject(3, periodo.getFimAulas());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar semestre", e);
        }
    }

    public void salvarDias(LocalDate dia, EventoCalendario evento, String periodo) {
        String sql = "INSERT INTO calendario (data, dia_semana, evento, periodo) VALUES (?, ?::dia_semana, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, dia);
            stmt.setObject(2, DiaSemana.fromDayOfWeek(dia.getDayOfWeek()).getValorBanco());
            stmt.setObject(3, evento.getValorBanco());
            stmt.setObject(4, periodo);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar dias", e);
        }
    }
}
