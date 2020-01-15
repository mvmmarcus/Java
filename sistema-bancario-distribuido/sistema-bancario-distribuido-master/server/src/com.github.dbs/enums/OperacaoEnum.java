package com.github.dbs.enums;

public enum OperacaoEnum {
    SAQUE("SAQUE"),
    DESPOSITO("DESPOSITO"),
    TRANSFERENCIA("TRANSFERENCIA");

    private final String valor;

    OperacaoEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
