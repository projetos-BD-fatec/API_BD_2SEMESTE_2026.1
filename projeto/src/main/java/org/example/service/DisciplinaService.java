package org.example.service;

import org.example.DAO.DisciplinaDAO;
import org.example.DAO.HorarioDAO;
import org.example.model.Disciplina;
import org.example.model.Horario;
import org.example.model.Usuario;
import org.example.util.UserSession;

import java.sql.SQLException;
import java.util.List;

public class DisciplinaService {

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final HorarioDAO horarioDAO = new HorarioDAO();

    public Long salvar(Disciplina disciplina, List<Horario> horarios, UserSession userSession) throws SQLException {
        Long disciplinaId = disciplinaDAO.salvarDisciplina(disciplina, userSession);

        horarios.forEach(h -> h.setDisciplinaId(disciplinaId));
        horarioDAO.salvarHorario(horarios);

        return disciplinaId;
    }

    public void deletarDisciplinas(Usuario user) {
        try {
            List<Disciplina> disciplinas = disciplinaDAO.disciplinasDoUsuario(user);
            for (Disciplina disciplina : disciplinas) {
                disciplinaDAO.deletarDisciplina(disciplina.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar disciplinas", e);
        }
    }
}
