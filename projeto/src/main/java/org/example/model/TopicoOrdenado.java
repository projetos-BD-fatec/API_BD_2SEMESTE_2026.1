package org.example.model;

public class TopicoOrdenado {
    private final Topico topico;
    private final int posicao;

    public TopicoOrdenado(Topico topico, int posicao) {
        this.topico = topico;
        this.posicao = posicao;
    }

    public Topico getTopico() {
        return topico;
    }

    public int getPosicao() {
        return posicao;
    }

    public Long    getId()       { return topico.getId();}
    public Integer     getPeso()     { return topico.getPeso(); }
    public Integer     getMinAulas() { return topico.getMinAulas(); }
    public Integer     getMaxAulas() { return topico.getMaxAulas(); }
    public String  getNome()     { return topico.getNome(); }

    public boolean isAvaliacao() {
        return Boolean.TRUE.equals(topico.isAvaliacao());
    }
}
