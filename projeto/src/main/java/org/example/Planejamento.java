package org.example;

import java.time.format.DateTimeFormatter;

public class Planejamento {
    private String data;
    private String dia;
    private String horario;
    private String evento;
    private String conteudo;

    public Planejamento(String data, String dia, String horario, String evento, String conteudo) {
        this.data = data;
        this.dia = dia;
        this.horario = horario;
        this.evento = evento;
        this.conteudo = conteudo;
    }

    public String getData() {
        return data;
    }

    public String getDia() {
        return dia;
    }

    public String getHorario() {
        return horario;
    }

    public String getEvento() {
        return evento;
    }

    public String getConteudo() {
        return conteudo;
    }
}
