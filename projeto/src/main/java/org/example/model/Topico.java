package org.example.model;

public class Topico {
    private Long id;
    private String nome;
    private int minAulas;
    private int maxAulas;
    private int peso;
    private Long disciplinaId;

    public Topico(String nome, int minAulas, int maxAulas, int peso, Long disciplinaId) {
        this.nome = nome;
        this.minAulas = minAulas;
        this.maxAulas = maxAulas;
        this.peso = peso;
        this.disciplinaId = disciplinaId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getMinAulas() { return minAulas; }
    public void setMinAulas(int minAulas) { this.minAulas = minAulas; }

    public int getMaxAulas() { return maxAulas; }
    public void setMaxAulas(int maxAulas) { this.maxAulas = maxAulas; }

    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }

    public Long getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(Long disciplinaId) { this.disciplinaId = disciplinaId; }
}
