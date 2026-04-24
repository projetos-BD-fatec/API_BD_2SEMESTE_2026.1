package org.example.model;

import java.time.LocalDate;

public class Calendario {
    private LocalDate data;
    private DiaSemana diaSemana;
    private String evento;

    public Calendario(LocalDate data, DiaSemana diaSemana, String evento) {
        this.data = data;
        this.diaSemana = diaSemana;
        this.evento = evento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}
