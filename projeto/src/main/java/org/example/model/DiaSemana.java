package org.example.model;

public class DiaSemana {
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

}
