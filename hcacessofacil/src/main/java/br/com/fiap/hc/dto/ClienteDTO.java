package br.com.fiap.hc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    private String nome;

    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "O telefone não pode estar vazio")
    private String telefone;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
