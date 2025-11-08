package br.com.fiap.dto.exame;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class AtualizarExameDto {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private int idCliente;

    @NotBlank(message = "A descrição do exame é obrigatória.")
    private String descricao;

    @NotNull(message = "A data do atendimento é obrigatória.")
    private LocalDateTime dataAtendimento;

    @NotBlank(message = "O status é obrigatório.")
    private String status;

    // Getters e Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
