package org.example.infrastructure;

import org.example.model.EventoIcal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ICalendarClient {

    private static final String URL_ICAL = "https://calendar.google.com/calendar/ical/unrbf7j3j4fjtvdjjmmb7ltdjs%40group.calendar.google.com/public/basic.ics";

    public List<EventoIcal> buscarEventos() throws IOException {
        String conteudo = baixarConteudo();
        return parsear(conteudo);
    }

    private String baixarConteudo() throws IOException {
        URL url = new URL(URL_ICAL);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        BufferedReader leitor = new BufferedReader(
                new InputStreamReader(conexao.getInputStream())
        );

        StringBuilder conteudo = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            conteudo.append(linha).append("\n");
        }

        leitor.close();
        return conteudo.toString();
    }

    private List<EventoIcal> parsear(String conteudo) {
        List<EventoIcal> eventos = new ArrayList<>();
        String[] linhas = conteudo.split("\n");

        String summary = null;
        LocalDate dataInicio = null;
        LocalDate dataFim = null;

        for (String linha : linhas) {
            if (linha.startsWith("SUMMARY:")) {
                summary = linha.substring("SUMMARY:".length()).trim();

            } else if (linha.startsWith("DTSTART")) {
                dataInicio = extrairData(linha);

            } else if (linha.startsWith("DTEND")) {
                dataFim = extrairData(linha);

            } else if (linha.equals("END:VEVENT")) {
                if (summary != null && dataInicio != null && dataFim != null) {
                    eventos.add(new EventoIcal(summary, dataInicio, dataFim));
                }
                summary = null;
                dataInicio = null;
                dataFim = null;
            }
        }

        return eventos;
    }

    private LocalDate extrairData(String linha) {
        String valor = linha.substring(linha.indexOf(":") + 1).trim();
        return LocalDate.parse(valor, DateTimeFormatter.BASIC_ISO_DATE);
    }
}