package br.com.fiap.dto.atendimento;

import br.com.fiap.model.AtendimentoStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class ListarAtendimentoDto {

    private int codigo;

    @JsonProperty("cliente_id")
    private int clienteId;

    @JsonProperty("especialista_id")
    private int especialistaId;

    private String descricao;
    private AtendimentoStatus status;
    private LocalDateTime dataAtendimento;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(int especialistaId) {
        this.especialistaId = especialistaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public AtendimentoStatus getStatus() {
        return status;
    }

    public void setStatus(AtendimentoStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }
}
