package org.example.service;

import org.example.DAO.AulaDAO;
import org.example.DAO.CalendarioDAO;
import org.example.DAO.HorarioDAO;
import org.example.model.Aula;
import org.example.model.Calendario;
import org.example.model.DiaSemana;
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

    public void gerarAulas(Long disciplinaId, int cargaHoraria) {
        List<Horario> horarios = horarioDAO.findByDisciplinaID(disciplinaId);
        Integer cargaTotal = 0;

        for (Horario horario : horarios) {
            List<Calendario> datasHorario = calendarioDAO.findByDiaSemana(horario.getDiaSemana());
            for (Calendario calendario : datasHorario) {
                LocalTime horaAtual = horario.getHoraInicio();
                while (!horaAtual.plusMinutes(50).isAfter(horario.getHoraFim())) {
                    Aula aula = new Aula(null, disciplinaId, calendario.getData(), null,
                            horaAtual, horaAtual.plusMinutes(50), null, null);
                    aulaDAO.salvar(aula);
                    horaAtual = horaAtual.plusMinutes(50);
                    cargaTotal += 50;
                }
            }
        }

        if (cargaTotal < cargaHoraria) {
            List<Calendario> sabados = calendarioDAO.findByDiaSemana(DiaSemana.SABADO);
            for (int i = sabados.size() - 1; i >= 0; i--) {
                Calendario calendario = sabados.get(i);
                if (cargaTotal >= cargaHoraria) break;

                int aulasDoDia = 0;
                LocalTime horaAtual = LocalTime.of(8, 0);

                while (cargaTotal < cargaHoraria && aulasDoDia < 3) {
                    Aula aula = new Aula(null, disciplinaId, calendario.getData(), null,
                            horaAtual, horaAtual.plusMinutes(50), null, null);
                    aulaDAO.salvar(aula);
                    horaAtual = horaAtual.plusMinutes(50);
                    cargaTotal += 50;
                    aulasDoDia++;
                }
            }
        }
    }


    public List<Aula> buscarAulas(Long disciplinaId) {
        return aulaDAO.findByDisciplinaId(disciplinaId);
    }
}
