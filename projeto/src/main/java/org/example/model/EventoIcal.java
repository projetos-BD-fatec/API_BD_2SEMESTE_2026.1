package org.example.model;

import java.time.LocalDate;

public class EventoIcal {
    private String summary;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public EventoIcal(String summary, LocalDate dataInicio, LocalDate dataFim) {
        this.summary = summary;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
