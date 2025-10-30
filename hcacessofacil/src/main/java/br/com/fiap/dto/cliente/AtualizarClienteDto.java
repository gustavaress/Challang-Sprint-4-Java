package br.com.fiap.dto.cliente;

import jakarta.validation.constraints.*;

public class AtualizarClienteDto {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(
            regexp = "\\d{11}",
            message = "O CPF deve conter exatamente 11 dígitos numéricos."
    )
    private String cpf;

    @Min(value = 1, message = "A idade mínima permitida é 1.")
    @Max(value = 120, message = "A idade máxima permitida é 120.")
    private int idade;

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(
            regexp = "\\d{10,11}",
            message = "O telefone deve ter entre 10 e 11 dígitos numéricos."
    )
    private String telefone1;

    // getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getTelefone1() { return telefone1; }
    public void setTelefone1(String telefone1) { this.telefone1 = telefone1; }
}
