package br.com.fiap.hc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AtendimentoDTO {

    private Long id;

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long idCliente;

    @NotNull(message = "O ID do especialista é obrigatório")
    private Long idEspecialista;

    private String descricao;

    @NotNull(message = "A data de atendimento é obrigatória")
    private LocalDateTime dataAtendimento;

    @NotBlank(message = "O status é obrigatório")
    private String status;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdEspecialista() {
        return idEspecialista;
    }
    public void setIdEspecialista(Long idEspecialista) {
        this.idEspecialista = idEspecialista;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }
    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
