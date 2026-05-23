package org.example.util;

import org.example.model.Usuario;

public class UserSession {
    private static UserSession instance;
    private Usuario usuarioLogado;

    private UserSession(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public static void iniciarSessao(Usuario usuario) {
        instance = new UserSession(usuario);
    }

    public static UserSession getInstance() {
        return instance;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void encerrarSessao() {
        instance = null;
    }
}