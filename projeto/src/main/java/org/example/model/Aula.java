package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Aula {
    private Long id;
    private Long disciplinaId;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Long topicoId;

    public Aula(Long id, Long disciplinaId, LocalDate data, LocalTime horaInicio, LocalTime horaFim, Long topicoId) {
        this.id = id;
        this.disciplinaId = disciplinaId;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.topicoId = topicoId;
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
