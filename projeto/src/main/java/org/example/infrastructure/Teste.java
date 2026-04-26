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

        //Teste realizado apenas uma vez para testar o metodo gerarAulas() e inserir dados na tabela Aula.
        AulaService aulaService = new AulaService(
                new HorarioDAO(),
                new CalendarioDAO(),
                new AulaDAO()
        );
        aulaService.gerarAulas(1L);
        System.out.println("Aulas geradas com sucesso!");
    }
}
