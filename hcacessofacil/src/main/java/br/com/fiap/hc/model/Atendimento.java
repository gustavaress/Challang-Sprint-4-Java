package br.com.fiap.hc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_ATENDIMENTO")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATENDIMENTO")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_ESPECIALISTA")
    private Especialista especialista;

    @NotBlank(message = "A descrição é obrigatória.")
    @Column(name = "DS_ATENDIMENTO", nullable = false, length = 255)
    private String descricao;

    @Column(name = "DT_ATENDIMENTO", nullable = false)
    private LocalDateTime dataAtendimento;

    @Column(name = "ST_ATENDIMENTO", length = 30)
    private String status; // AGENDADO, CONFIRMADO, CANCELADO, REALIZADO

    public Atendimento() {}

    public Atendimento(Cliente cliente, Especialista especialista, String descricao, LocalDateTime dataAtendimento, String status) {
        this.cliente = cliente;
        this.especialista = especialista;
        this.descricao = descricao;
        this.dataAtendimento = dataAtendimento;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Especialista getEspecialista() { return especialista; }
    public void setEspecialista(Especialista especialista) { this.especialista = especialista; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataAtendimento() { return dataAtendimento; }
    public void setDataAtendimento(LocalDateTime dataAtendimento) { this.dataAtendimento = dataAtendimento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
