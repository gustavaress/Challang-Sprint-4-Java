package br.com.fiap.hc.exception;

public class CampoJaCadastrado extends RuntimeException {
    public CampoJaCadastrado(String campo) {
        super(campo + " já cadastrado nos sistema!");
    }
}