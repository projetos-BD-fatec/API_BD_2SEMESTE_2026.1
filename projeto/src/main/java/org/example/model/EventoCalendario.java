package org.example.model;

public enum EventoCalendario {
    LETIVO("Letivo"),
    INICIO_AULAS("Início das aulas"),
    FIM_AULAS("Fim das aulas"),
    KICKOFF("Kickoff"),
    PLANNING("Planning"),
    SPRINT_1("Sprint 1"),
    SPRINT1_1("Sprint 1 | Semana 1"),
    SPRINT1_2("Sprint 1 | Semana 2"),
    SPRINT1_3("Sprint 1 | Semana 3"),
    SPRINT_2("Sprint 2"),
    SPRINT2_1("Sprint 2 | Semana 1"),
    SPRINT2_2("Sprint 2 | Semana 2"),
    SPRINT2_3("Sprint 2 | Semana 3"),
    SPRINT_3("Sprint 3"),
    SPRINT3_1("Sprint 3 | Semana 1"),
    SPRINT3_2("Sprint 3 | Semana 2"),
    SPRINT3_3("Sprint 3 | Semana 3"),
    REVIEW_PLANNING("Review | Planning"),
    REVIEW("Sprint Review"),
    FEIRA("Feira de Soluções"),
    IGNORAR(null);

    private final String valorBanco;

    EventoCalendario(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public static EventoCalendario fromBanco(String valor) {
        for (EventoCalendario e : values()) {
            if (valor.equalsIgnoreCase(e.valorBanco)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Evento inválido: " + valor);
    }
}
