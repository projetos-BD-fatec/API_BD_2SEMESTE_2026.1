package org.example.service;

import org.example.infrastructure.ICalendarClient;
import org.example.model.EventoCalendario;
import org.example.model.EventoIcal;
import org.example.model.Periodo;
import org.example.model.ResumoPeriodo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CalendarioService {

    private final ICalendarClient client;

    public CalendarioService() {
        this.client = new ICalendarClient();
    }

    public ResumoPeriodo buscarResumo(String periodo) throws IOException {
        List<EventoIcal> eventos = client.buscarEventos();
        EventoIcal sprintUm = null;
        EventoIcal inicioAulasEvento = null;
        EventoIcal fimAulasEvento = null;
        EventoIcal feiraEvento = null;
        for (EventoIcal evento : eventos) {
            EventoCalendario classificado = EventoAcadClassificador.classificar(evento.getSummary());

            if (classificado == EventoCalendario.SPRINT_1) sprintUm = evento;
            if (classificado == EventoCalendario.INICIO_AULAS) inicioAulasEvento = evento;
            if (classificado == EventoCalendario.FIM_AULAS) fimAulasEvento = evento;
            if (classificado == EventoCalendario.FEIRA) feiraEvento = evento;
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

        return new ResumoPeriodo(periodo, inicioAulas, fimAulas, kickoff, planning, sprint1, sprint2, sprint3, reviewPlanning1, reviewPlanning2, review, feira);
    }

}
