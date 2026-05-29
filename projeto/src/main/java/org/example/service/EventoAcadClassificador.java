package org.example.service;

import org.example.model.EventoCalendario;

public class EventoAcadClassificador {

    public static EventoCalendario classificar(String summary) {
        String texto = summary.toLowerCase();
        if (texto.contains("feriado") || texto.contains("recesso") || texto.contains("não haverá aula") ||texto.contains("emenda")) return EventoCalendario.IGNORAR;
        if (texto.contains("início das aulas")) return EventoCalendario.INICIO_AULAS;
        if (texto.contains("encerramento") || texto.contains("término das atividades didáticas")) return EventoCalendario.FIM_AULAS;
        if (texto.contains("kickoff")) return EventoCalendario.KICKOFF;
        if (texto.contains("planning")) return EventoCalendario.PLANNING;
        if (texto.contains("sprint 1") || texto.contains("primeira sprint")) return  EventoCalendario.SPRINT_1;
        if (texto.contains("sprint 2")  || texto.contains("segunda sprint")) return EventoCalendario.SPRINT_2;
        if (texto.contains("sprint 3")  || texto.contains("terceira sprint")) return EventoCalendario.SPRINT_3;
        if (texto.contains("review / planning")) return EventoCalendario.REVIEW_PLANNING;
        if (texto.contains("review")) return EventoCalendario.REVIEW;
        if (texto.contains("feira de soluções")) return EventoCalendario.FEIRA;

        return EventoCalendario.LETIVO;
    }
}
