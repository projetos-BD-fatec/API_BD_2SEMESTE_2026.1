package org.example.model;

import java.time.DayOfWeek;

import static java.time.DayOfWeek.*;

public enum DiaSemana {
    SEGUNDA("Segunda"),
    TERCA("Terça"),
    QUARTA("Quarta"),
    QUINTA("Quinta"),
    SEXTA("Sexta"),
    SABADO("Sábado");

    private final String valorBanco;

    DiaSemana(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public static DiaSemana fromBanco(String valor) {
        for (DiaSemana d : values()) {
            if (d.valorBanco.equalsIgnoreCase(valor)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Dia inválido: " + valor);
    }

    @Override
    public String toString() {
        return valorBanco;
    }

    public static DiaSemana fromDayOfWeek(DayOfWeek dow) {
        switch (dow) {
            case MONDAY:    return DiaSemana.SEGUNDA;
            case TUESDAY:   return DiaSemana.TERCA;
            case WEDNESDAY: return DiaSemana.QUARTA;
            case THURSDAY:  return DiaSemana.QUINTA;
            case FRIDAY:    return DiaSemana.SEXTA;
            case SATURDAY:  return DiaSemana.SABADO;
            default:        throw new IllegalArgumentException("Dia não letivo: " + dow);
        }
    }

}
