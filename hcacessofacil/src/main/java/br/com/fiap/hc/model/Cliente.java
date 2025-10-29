package br.com.fiap.hc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "T_CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio.")
    @Column(name = "NM_CLIENTE", nullable = false, length = 100)
    private String nome;

    @Email(message = "E-mail inválido.")
    @Column(name = "DS_EMAIL", unique = true, length = 100)
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    @Column(name = "NR_TELEFONE", length = 20)
    private String telefone;

    // Construtores
    public Cliente() {}
    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
