package br.com.fiap.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Atendimento {

    private int codigo;
    private Cliente cliente;
    private Especialista especialista;
    private String descricao;
    private LocalDateTime dataAtendimento;
    private AtendimentoStatus status;

    public Atendimento() {}

    public Atendimento(int codigo, Cliente cliente, Especialista especialista,
                       String descricao, LocalDateTime dataAtendimento,
                       AtendimentoStatus status) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.especialista = especialista;
        this.descricao = descricao;
        this.dataAtendimento = dataAtendimento;
        this.status = status;
    }

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

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
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

    public String getDataAtendimentoFormatada() {
        if (dataAtendimento == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dataAtendimento.format(formatter);
    }

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nCliente: " + (cliente != null ? cliente.getCodigo() : "N/A") +
                "\nEspecialista: " + (especialista != null ? especialista.getCodigo() : "N/A") +
                "\nStatus: " + status +
                "\nData: " + (dataAtendimento != null ? getDataAtendimentoFormatada() : "N/A") +
                "\nDescrição: " + descricao;
    }
}
