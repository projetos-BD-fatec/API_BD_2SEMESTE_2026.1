package org.example.DAO;

import org.example.infrastructure.ConexaoBD;
import org.example.model.Disciplina;
import org.example.util.UserSession;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public List<Disciplina> findByUsuarioId(Long usuarioId, String periodo) {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT id, nome, carga_horaria, curso, semestre FROM disciplina WHERE usuario_id = ? AND periodo = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuarioId);
            stmt.setString(2, periodo);
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
    public Long salvarDisciplina(Disciplina disciplina, UserSession userSession) {
        String sql = "INSERT INTO disciplina (nome, carga_horaria, curso, semestre, usuario_id, periodo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setString(3, disciplina.getCurso());
            stmt.setInt(4, disciplina.getSemestre());
            stmt.setLong(5, userSession.getUsuarioLogado().getId());
            stmt.setString(6, userSession.getUsuarioLogado().getPeriodoAtual());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Long idGerado = rs.getLong(1);
                disciplina.setId(idGerado);
                return idGerado;
            }
            throw new RuntimeException("Nenhum ID gerado ao salvar disciplina");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar disciplina", e);
        }
    }
}