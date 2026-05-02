package org.example;

public class Aula {

    private String dia;
    private String horario;
    private Integer aulas;

    public Aula(String dia, String horario, Integer aulas) {
        this.dia = dia;
        this.horario = horario;
        this.aulas = aulas;
    }

    public String getDia() { return dia; }
    public String getHorario() { return horario; }
    public Integer getAulas() { return aulas; }
}
