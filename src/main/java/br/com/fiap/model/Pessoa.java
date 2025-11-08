package br.com.fiap.model;

import java.time.LocalDate;

public abstract class Pessoa {

    private int codigo;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone1;

    public Pessoa() {}

    public Pessoa(int codigo, String nome, String email, String cpf, LocalDate dataNascimento, String telefone1) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone1 = telefone1;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nNome: " + nome +
                "\nEmail: " + email +
                "\nCPF: " + cpf +
                "\nData de Nascimento: " + dataNascimento +
                "\nTelefone: " + telefone1;
    }
}
