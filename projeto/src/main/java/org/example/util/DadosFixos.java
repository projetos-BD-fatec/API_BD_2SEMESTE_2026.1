package org.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
