package br.com.fiap.hc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "T_ESPECIALISTA")
public class Especialista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESPECIALISTA")
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(name = "NM_ESPECIALISTA", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A especialidade é obrigatória.")
    @Column(name = "DS_ESPECIALIDADE", length = 100)
    private String especialidade;

    @NotBlank(message = "O CRM é obrigatório.")
    @Column(name = "NR_CRM", unique = true, length = 20)
    private String crm;

    public Especialista() {}
    public Especialista(String nome, String especialidade, String crm) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.crm = crm;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
}
