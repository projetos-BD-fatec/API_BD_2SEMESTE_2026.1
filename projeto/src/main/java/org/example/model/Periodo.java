package org.example.model;

import java.time.LocalDate;

public class Periodo {
        private LocalDate inicio;
        private LocalDate fim;

    public Periodo(LocalDate inicio, LocalDate fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }
}
