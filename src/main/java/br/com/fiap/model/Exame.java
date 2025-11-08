package br.com.fiap.model;

import java.time.LocalDateTime;

public class Exame {

    private int codigo;
    private Cliente cliente;
    private String descricao;
    private LocalDateTime dataAtendimento;
    private AtendimentoStatus status;

    public Exame() {}

    public Exame(int codigo, Cliente cliente, String descricao, LocalDateTime dataAtendimento, AtendimentoStatus status) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.descricao = descricao;
        this.dataAtendimento = dataAtendimento;
        this.status = status;
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nCliente: " + (cliente != null ? cliente.getNome() : "N/A") +
                "\nDescrição: " + descricao +
                "\nData do Atendimento: " + dataAtendimento +
                "\nStatus: " + status;
    }
}
