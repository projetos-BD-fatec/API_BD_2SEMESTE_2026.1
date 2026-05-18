package org.example;

import org.example.model.Usuario;
import org.example.DAO.UsuarioDAO;

public class test_usuario {
    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        System.out.println("--- TESTANDO STORY 12 (AUTENTICAÇÃO) ---");

        System.out.println("\n[TESTE 1] Tentando fazer login com dados CORRETOS...");

        String emailValido = "mineda@fatec.sp.gov.br";
        String cpfValido = "12345678900";

        Usuario loginSucesso = dao.login(emailValido, cpfValido);

        if (loginSucesso != null) {
            System.out.println(" RESULTADO: Sucesso! Professor localizado: " + loginSucesso.getNome());
        } else {
            System.out.println(" RESULTADO: Usuário ainda não existe no banco.");
        }

        System.out.println("\n[TESTE 2] Tentando fazer login com senha (CPF) ERRADA...");
        Usuario loginErro = dao.login(emailValido, "00000000000");

        if (loginErro != null) {
            System.out.println(" RESULTADO: Erro crítico! Passou com senha errada.");
        } else {
            System.out.println(" RESULTADO: Sucesso no teste! Sistema barrou o acesso.");
        }
    }
}