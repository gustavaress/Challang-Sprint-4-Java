package br.com.fiap.model;

import java.time.LocalDate;

public class Cliente extends Pessoa {

    private String convenio;
    private String numeroCarteirinha;

    public Cliente() {}

    public Cliente(int codigo, String nome, String email, String cpf, LocalDate dataNascimento,
                   String telefone1, String convenio, String numeroCarteirinha) {
        super(codigo, nome, email, cpf, dataNascimento, telefone1);
        this.convenio = convenio;
        this.numeroCarteirinha = numeroCarteirinha;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getNumeroCarteirinha() {
        return numeroCarteirinha;
    }

    public void setNumeroCarteirinha(String numeroCarteirinha) {
        this.numeroCarteirinha = numeroCarteirinha;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nConvênio: " + convenio +
                "\nNúmero da Carteirinha: " + numeroCarteirinha;
    }
}
