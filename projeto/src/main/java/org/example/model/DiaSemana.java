package org.example.model;

import java.time.DayOfWeek;

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
        return switch (dow) {
            case MONDAY    -> DiaSemana.SEGUNDA;
            case TUESDAY   -> DiaSemana.TERCA;
            case WEDNESDAY -> DiaSemana.QUARTA;
            case THURSDAY  -> DiaSemana.QUINTA;
            case FRIDAY    -> DiaSemana.SEXTA;
            case SATURDAY  -> DiaSemana.SABADO;
            default        -> throw new IllegalArgumentException("Dia não letivo: " + dow);
        };
    }

}
