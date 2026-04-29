package org.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;


import java.time.LocalTime;

public class DadosFixos {
    public static final ObservableList<LocalTime> HORARIOS = FXCollections.observableArrayList(
            LocalTime.of(18, 45),
            LocalTime.of(19, 35),
            LocalTime.of(20, 25),
            LocalTime.of(21, 25),
            LocalTime.of(22, 15),
            LocalTime.of(23, 0)
    );

    public static final List<String> CURSOS = List.of(
            "Banco de Dados",
            "Logística",
            "Análise e Desenvolvimento de Sistemas",
            "Desenvolvimento de Software Multiplataforma",
            "Engenharia de Produção",
            "Gestão de Produção Industrial",
            "Manutenção de Aeronaves",
            "Manufatura Avançada",
            "Gestão Empresaral EAD"
            // consultar site da instituição para lista completa
    );

    public static final List<String> SEMESTRES = List.of(
            "1º Sem", "2º Sem", "3º Sem",
            "4º Sem", "5º Sem", "6º Sem"
    );

    public static final List<Integer> CARGAS_HORARIAS = List.of(40, 80);
}
