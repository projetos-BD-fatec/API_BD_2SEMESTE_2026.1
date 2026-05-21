package org.example.model;

import java.time.LocalDate;

public class ResumoPeriodo {
    private String periodo;
    private LocalDate inicioAulas;
    private LocalDate fimAulas;
    private Periodo kickoff;
    private Periodo planning;
    private Periodo sprint1;
    private Periodo sprint2;
    private Periodo sprint3;
    private Periodo reviewPlanning1;
    private Periodo reviewPlanning2;
    private Periodo review;
    private LocalDate feira;

    public ResumoPeriodo(String periodo, LocalDate inicioAulas, LocalDate fimAulas, Periodo kickoff, Periodo planning, Periodo sprint1, Periodo sprint2, Periodo sprint3, Periodo reviewPlanning1, Periodo reviewPlanning2, Periodo review, LocalDate feira) {
        this.periodo = periodo;
        this.inicioAulas = inicioAulas;
        this.fimAulas = fimAulas;
        this.kickoff = kickoff;
        this.planning = planning;
        this.sprint1 = sprint1;
        this.sprint2 = sprint2;
        this.sprint3 = sprint3;
        this.reviewPlanning1 = reviewPlanning1;
        this.reviewPlanning2 = reviewPlanning2;
        this.review = review;
        this.feira = feira;
    }

    public String getPeriodo() {
        return periodo;
    }

    public LocalDate getInicioAulas() {
        return inicioAulas;
    }

    public LocalDate getFimAulas() {
        return fimAulas;
    }

    public Periodo getKickoff() {
        return kickoff;
    }

    public Periodo getPlanning() {
        return planning;
    }

    public Periodo getSprint1() {
        return sprint1;
    }

    public Periodo getSprint2() {
        return sprint2;
    }

    public Periodo getSprint3() {
        return sprint3;
    }

    public Periodo getReviewPlanning1() {
        return reviewPlanning1;
    }

    public Periodo getReviewPlanning2() {
        return reviewPlanning2;
    }

    public Periodo getReview() {
        return review;
    }

    public LocalDate getFeira() {
        return feira;
    }
}
