package org.example.service;

import org.example.model.EventoCalendario;

public class EventoAcadClassificador {

    public static EventoCalendario classificar(String summary) {
        String texto = summary.toLowerCase();
        if (texto.contains("feriado") || texto.contains("recesso") || texto.contains("emenda")) return EventoCalendario.IGNORAR;
        if (texto.contains("início")) return EventoCalendario.INICIO_AULAS;
        if (texto.contains("encerramento")) return EventoCalendario.FIM_AULAS;
        if (texto.contains("kickoff")) return EventoCalendario.KICKOFF;
        if (texto.contains("planning")) return EventoCalendario.PLANNING;
        if (texto.contains("sprint 1")) return  EventoCalendario.SPRINT_1;
        if (texto.contains("sprint 2")) return EventoCalendario.SPRINT_2;
        if (texto.contains("sprint 3")) return EventoCalendario.SPRINT_3;
        if (texto.contains("review / planning")) return EventoCalendario.REVIEW_PLANNING;
        if (texto.contains("review")) return EventoCalendario.REVIEW;
        if (texto.contains("feira")) return EventoCalendario.FEIRA;

        return EventoCalendario.LETIVO;
    }
}
