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

    /**
     * Salva a disciplina e seus horários no banco de dados.
     * 1. Insere a disciplina na tabela 'disciplina' e obtém o ID gerado.
     * 2. Associa o ID gerado a cada horário e insere na tabela 'horario'.
     *
     * @return ID da disciplina gerado pelo banco.
     */
    public Long salvar(Disciplina disciplina, List<Horario> horarios, UserSession userSession) throws SQLException {
        // Passo 1: salva a disciplina e recebe o ID gerado pelo banco
        Long disciplinaId = disciplinaDAO.salvarDisciplina(disciplina, userSession);

        // Passo 2: atribui o ID a cada horário e persiste
        horarios.forEach(h -> h.setDisciplinaId(disciplinaId));
        horarioDAO.SalvarHorario(horarios);

        return disciplinaId;
    }
}
