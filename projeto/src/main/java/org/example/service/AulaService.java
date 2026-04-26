package org.example.service;

import org.example.DAO.AulaDAO;
import org.example.DAO.CalendarioDAO;
import org.example.DAO.HorarioDAO;
import org.example.model.Aula;
import org.example.model.Calendario;
import org.example.model.Horario;

import java.time.LocalTime;
import java.util.List;

public class AulaService {
    private final HorarioDAO horarioDAO;
    private final CalendarioDAO calendarioDAO;
    private final AulaDAO aulaDAO;

    public AulaService(HorarioDAO horarioDAO, CalendarioDAO calendarioDAO, AulaDAO aulaDAO) {
        this.horarioDAO = horarioDAO;
        this.calendarioDAO = calendarioDAO;
        this.aulaDAO = aulaDAO;
    }

    public void gerarAulas(Long disciplinaId) {
        List<Horario> horarios = horarioDAO.findByDisciplinaID(disciplinaId);
        for(Horario horario : horarios) {
            List<Calendario> datas = calendarioDAO.findByDiaSemana(horario.getDiaSemana());
            for(Calendario calendario : datas) {
                LocalTime horaAtual = horario.getHoraInicio();
                while (!horaAtual.plusMinutes(50).isAfter(horario.getHoraFim())){
                    Aula aula = new Aula(null, disciplinaId, calendario.getData(), null, horaAtual, horaAtual.plusMinutes(50), null, null);
                    aulaDAO.salvar(aula);
                    horaAtual = horaAtual.plusMinutes(50);
                }
            }
        }
    }
}
