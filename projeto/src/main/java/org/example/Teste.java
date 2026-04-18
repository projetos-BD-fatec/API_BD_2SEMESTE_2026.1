package org.example;

import java.sql.Connection;

public class Teste {

    public static void main(String[] args) {

        try (Connection conn = Conexao.conectar()) {

            if (conn != null) {
                System.out.println("Conectado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Erro na conexão:");
            e.printStackTrace();
        }
    }
}
