package org.example.model;

public class Disciplina {
    private Long id;
    private String nome;
    private Integer cargaHoraria;
    private String curso;
    private Integer semestre;
    private Long usuarioId;
    private String periodo;

    public Disciplina(Long id, String nome, Integer cargaHoraria, String curso, Integer semestre) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.curso = curso;
        this.semestre = semestre;
    }

    public Disciplina(Long id, String nome, Integer cargaHoraria, String curso, Integer semestre, Long usuarioId, String periodo) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.curso = curso;
        this.semestre = semestre;
        this.usuarioId = usuarioId;
        this.periodo = periodo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }
}
