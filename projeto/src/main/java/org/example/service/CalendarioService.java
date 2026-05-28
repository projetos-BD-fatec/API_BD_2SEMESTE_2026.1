package org.example.service;

import org.example.DAO.CalendarioDAO;
import org.example.infrastructure.ICalendarClient;
import org.example.model.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CalendarioService {

    private final ICalendarClient client;
    private final CalendarioDAO calendarioDAO;
    public CalendarioService() {
        this.client = new ICalendarClient();
        this.calendarioDAO = new CalendarioDAO();
    }
    private boolean isDiaIgnorado(LocalDate dia, List<EventoIcal> eventosIgnorar) {
        for(EventoIcal evento : eventosIgnorar) {
            if (!dia.isBefore(evento.getDataInicio()) && !dia.isAfter(evento.getDataFim())) {
                return true;
            }
        }
        return false;
    }
    private EventoCalendario classificarDia(LocalDate dia, ResumoPeriodo resumo) {

        if (dentrodoPeriodo(dia, resumo.getKickoff()))       return EventoCalendario.KICKOFF;
        if (dentrodoPeriodo(dia, resumo.getPlanning()))      return EventoCalendario.PLANNING;
        if (dentrodoPeriodo(dia, resumo.getReviewPlanning1())) return EventoCalendario.REVIEW_PLANNING;
        if (dentrodoPeriodo(dia, resumo.getReviewPlanning2())) return EventoCalendario.REVIEW_PLANNING;
        if (dentrodoPeriodo(dia, resumo.getReview()))        return EventoCalendario.REVIEW;
        if (resumo.getFeira() != null && dia.equals(resumo.getFeira())) return EventoCalendario.FEIRA;

        if (dentrodoPeriodo(dia, resumo.getSprint1())) return calcularSemanaSprint(dia, resumo.getSprint1(), 1);
        if (dentrodoPeriodo(dia, resumo.getSprint2())) return calcularSemanaSprint(dia, resumo.getSprint2(), 2);
        if (dentrodoPeriodo(dia, resumo.getSprint3())) return calcularSemanaSprint(dia, resumo.getSprint3(), 3);

        return EventoCalendario.LETIVO;
    }

    private boolean dentrodoPeriodo(LocalDate dia, Periodo periodo) {
        return !dia.isBefore(periodo.getInicio()) && !dia.isAfter(periodo.getFim());
    }

    private EventoCalendario calcularSemanaSprint(LocalDate dia, Periodo sprint, int numeroSprint) {
        long diasDesdeInicio = ChronoUnit.DAYS.between(sprint.getInicio(), dia);
        int semana = (int) (diasDesdeInicio / 7) + 1;
        if (semana > 3) semana = 3;

        if (numeroSprint == 1) {
            if (semana == 1) return EventoCalendario.SPRINT1_1;
            if (semana == 2) return EventoCalendario.SPRINT1_2;
            return EventoCalendario.SPRINT1_3;
        }
        if (numeroSprint == 2) {
            if (semana == 1) return EventoCalendario.SPRINT2_1;
            if (semana == 2) return EventoCalendario.SPRINT2_2;
            return EventoCalendario.SPRINT2_3;
        }
        if (numeroSprint == 3) {
            if (semana == 1) return EventoCalendario.SPRINT3_1;
            if (semana == 2) return EventoCalendario.SPRINT3_2;
            return EventoCalendario.SPRINT3_3;
        }
        return EventoCalendario.LETIVO;
    }

    public ResumoPeriodo buscarResumo(int ano, int semestre) throws IOException {
        String periodo = ano + "." + semestre;
        LocalDate filtroInicio = semestre == 1
                ? LocalDate.of(ano, 1, 1)
                : LocalDate.of(ano, 7, 1);
        LocalDate filtroFim = semestre == 1
                ? LocalDate.of(ano, 7, 31)
                : LocalDate.of(ano, 12, 31);
        List<EventoIcal> eventos = client.buscarEventos();
        EventoIcal sprintUm = null;
        EventoIcal inicioAulasEvento = null;
        EventoIcal fimAulasEvento = null;
        EventoIcal feiraEvento = null;
        List<EventoIcal> eventosIgnorar = new ArrayList<>();
        for (EventoIcal evento : eventos) {
            if (evento.getDataInicio().isBefore(filtroInicio) ||
                    evento.getDataInicio().isAfter(filtroFim)) continue;
            EventoCalendario classificado = EventoAcadClassificador.classificar(evento.getSummary());

            if (classificado == EventoCalendario.SPRINT_1) sprintUm = evento;
            if (classificado == EventoCalendario.INICIO_AULAS) inicioAulasEvento = evento;
            if (classificado == EventoCalendario.FIM_AULAS) fimAulasEvento = evento;
            if (classificado == EventoCalendario.FEIRA) feiraEvento = evento;
            if (classificado == EventoCalendario.IGNORAR) eventosIgnorar.add(evento);
        }

        if (sprintUm == null || inicioAulasEvento == null || fimAulasEvento == null) return null;

        LocalDate inicioAulas = inicioAulasEvento.getDataInicio();
        LocalDate fimAulas = fimAulasEvento.getDataInicio();
        LocalDate feira = feiraEvento != null ? feiraEvento.getDataInicio() : null;
        LocalDate sprintUmInicio = sprintUm.getDataInicio();
        LocalDate inicioPlanning = sprintUmInicio.minusWeeks(1);
        LocalDate inicioKickoff  = sprintUmInicio.minusWeeks(2);
        LocalDate inicioReview1 = sprintUmInicio.plusWeeks(3);
        LocalDate inicioSprint2 = sprintUmInicio.plusWeeks(4);
        LocalDate inicioReview2 = sprintUmInicio.plusWeeks(7);
        LocalDate inicioSprint3 = sprintUmInicio.plusWeeks(8);
        LocalDate inicioReview3 = sprintUmInicio.plusWeeks(11);

        Periodo kickoff = new Periodo(inicioKickoff, inicioPlanning.minusDays(1));
        Periodo planning = new Periodo(inicioPlanning, sprintUmInicio.minusDays(1));
        Periodo sprint1 = new Periodo(sprintUmInicio, inicioReview1.minusDays(1));
        Periodo reviewPlanning1 = new Periodo(inicioReview1, inicioSprint2.minusDays(1));
        Periodo sprint2 = new Periodo(inicioSprint2, inicioReview2.minusDays(1));
        Periodo reviewPlanning2 = new Periodo(inicioReview2, inicioSprint3.minusDays(1));
        Periodo sprint3 = new Periodo(inicioSprint3, inicioReview3.minusDays(1));
        Periodo review = new Periodo(inicioReview3, inicioReview3.plusDays(4));

        return new ResumoPeriodo(periodo, inicioAulas, fimAulas, kickoff, planning, sprint1, sprint2, sprint3, reviewPlanning1, reviewPlanning2, review, feira, eventosIgnorar);
    }

    public void iniciarSemestre(ResumoPeriodo resumo) {
        calendarioDAO.salvarSemestre(resumo);
        LocalDate dia = resumo.getInicioAulas();
        while (!dia.isAfter(resumo.getFimAulas())) {
                if (dia.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    dia = dia.plusDays(1);
                    continue;
                }
            if (isDiaIgnorado(dia, resumo.getEventosIgnorar())) {
                dia = dia.plusDays(1);
                continue;
            }
            EventoCalendario evento = classificarDia(dia, resumo);
            calendarioDAO.salvarDias(dia, evento, resumo.getPeriodo());
            dia = dia.plusDays(1);
        }
    }
}
