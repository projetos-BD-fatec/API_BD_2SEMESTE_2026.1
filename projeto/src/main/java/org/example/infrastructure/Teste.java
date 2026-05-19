package org.example.infrastructure;

import org.example.DAO.AulaDAO;
import org.example.DAO.CalendarioDAO;
import org.example.DAO.HorarioDAO;
import org.example.service.AulaService;

import java.sql.Connection;

public class Teste {

    public static void main(String[] args) {

        try (Connection conn = ConexaoBD.conectar()) {

            if (conn != null) {
                System.out.println("Conectado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Erro na conexão:");
            e.printStackTrace();
        }


    }


}
