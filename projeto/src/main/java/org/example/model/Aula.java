package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Aula {
    private Long id;
    private Long disciplinaId;
    private LocalDate data;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String evento;
    private Long topicoId;
    private boolean ancorada = false;

    public Aula(Long id, Long disciplinaId, LocalDate data, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim, String evento, Long topicoId) {
        this.id = id;
        this.disciplinaId = disciplinaId;
        this.data = data;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.evento = evento;
        this.topicoId = topicoId;
    }

    public boolean isAncorada() {
        return ancorada;
    }

    public void setAncorada(boolean ancorada) {
        this.ancorada = ancorada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Long getTopicoId() {
        return topicoId;
    }

    public void setTopicoId(Long topicoId) {
        this.topicoId = topicoId;
    }
}
