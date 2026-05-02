package org.example.model;

public class Topico {
    private Long id;
    private String nome;
    private Integer minAulas;
    private Integer maxAulas;
    private Integer peso;
    private Long disciplinaId;
    private Boolean avaliacao;
    private Integer ordem;

    public Topico(String nome, Integer minAulas, Integer maxAulas, Integer peso, Long disciplinaId, Boolean avaliacao, Integer ordem) {
        this.nome = nome;
        this.minAulas = minAulas;
        this.maxAulas = maxAulas;
        this.peso = peso;
        this.disciplinaId = disciplinaId;
        this.avaliacao = avaliacao;
        this.ordem = ordem;
    }

    public Topico(Long id, String nome, int minAulas, int maxAulas, int peso, Long disciplinaId, Boolean avaliacao, Integer ordem) {
        this.id = id;
        this.nome = nome;
        this.minAulas = minAulas;
        this.maxAulas = maxAulas;
        this.peso = peso;
        this.disciplinaId = disciplinaId;
        this.avaliacao = avaliacao;
        this.ordem = ordem;
    }

    public Boolean isAvaliacao() {
        return avaliacao;
    }

    public Boolean getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Boolean avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getMinAulas() { return minAulas; }
    public void setMinAulas(Integer minAulas) { this.minAulas = minAulas; }

    public Integer getMaxAulas() { return maxAulas; }
    public void setMaxAulas(Integer maxAulas) { this.maxAulas = maxAulas; }

    public Integer getPeso() { return peso; }
    public void setPeso(Integer peso) { this.peso = peso; }

    public Long getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(Long disciplinaId) { this.disciplinaId = disciplinaId; }
}
