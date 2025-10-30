package br.com.fiap.dto.atendimento;

import br.com.fiap.model.AtendimentoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CadastroAtendimentoDto {

    @JsonProperty("cliente_id")
    @NotNull(message = "O cliente é obrigatório.")
    private Integer clienteId;

    @JsonProperty("especialista_id")
    @NotNull(message = "O especialista é obrigatório.")
    private Integer especialistaId;

    @NotNull(message = "A descrição é obrigatória.")
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "A data deve estar no futuro.")
    private LocalDateTime dataAtendimento;

    @NotNull(message = "O status é obrigatório.")
    private AtendimentoStatus status;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(Integer especialistaId) {
        this.especialistaId = especialistaId;
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

    public AtendimentoStatus getStatus() {
        return status;
    }

    public void setStatus(AtendimentoStatus status) {
        this.status = status;
    }
}
