package br.com.fiap.model;

public class Cliente extends Pessoa {

    public Cliente() {}

    public Cliente(int codigo, String nome, String email, String cpf, int idade, String telefone1) {
        super(codigo, nome, email, cpf, idade, telefone1);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
