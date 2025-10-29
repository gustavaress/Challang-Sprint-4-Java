package br.com.fiap.hc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EspecialistaDTO {

    private Long id;

    @NotBlank(message = "O nome do especialista é obrigatório")
    @Size(min = 3, max = 80)
    private String nome;

    @NotBlank(message = "A especialidade é obrigatória")
    private String especialidade;

    @NotBlank(message = "O CRM é obrigatório")
    @Size(min = 4, max = 20)
    private String crm;

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

    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }
    public void setCrm(String crm) {
        this.crm = crm;
    }
}
