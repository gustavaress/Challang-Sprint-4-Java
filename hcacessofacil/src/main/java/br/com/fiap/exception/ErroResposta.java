package br.com.fiap.exception;

public class ErroResposta {

    private String mensagem;

    public ErroResposta(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
