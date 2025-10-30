package br.com.fiap.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AtendimentoStatus {
    AGENDADO("Agendado"),
    CONFIRMADO("Confirmado"),
    CANCELADO("Cancelado"),
    REALIZADO("Realizado");

    private final String valorBanco;

    AtendimentoStatus(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public static AtendimentoStatus fromDbValue(String valorBanco) {
        if (valorBanco == null) return null;

        String normalizado = valorBanco.trim().toUpperCase().replace(" ", "_");

        for (AtendimentoStatus st : values()) {
            if (st.valorBanco.equalsIgnoreCase(valorBanco) ||
                    st.name().equalsIgnoreCase(normalizado)) {
                return st;
            }
        }

        throw new IllegalArgumentException("Status inválido no banco: " + valorBanco);
    }

    @JsonCreator
    public static AtendimentoStatus fromJson(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        String normalizado = valor.trim().toUpperCase().replace(" ", "_");

        for (AtendimentoStatus status : values()) {
            if (status.name().equalsIgnoreCase(normalizado)
                    || status.getValorBanco().equalsIgnoreCase(valor)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + valor);
    }
}
