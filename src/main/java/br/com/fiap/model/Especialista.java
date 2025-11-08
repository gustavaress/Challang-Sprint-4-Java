package br.com.fiap.model;

import java.time.LocalDate;

public class Especialista extends Pessoa {

    private String crm;
    private String especialidade;

    public Especialista() {}

    public Especialista(int codigo, String nome, String email, String cpf, LocalDate dataNascimento,
                        String telefone1, String crm, String especialidade) {
        super(codigo, nome, email, cpf, dataNascimento, telefone1);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nCRM: " + crm +
                "\nEspecialidade: " + especialidade;
    }
}
