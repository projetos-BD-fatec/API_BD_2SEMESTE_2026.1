package org.example.service;

import org.example.DAO.DisciplinaDAO;
import org.example.DAO.HorarioDAO;
import org.example.model.Disciplina;
import org.example.model.Horario;
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
}
