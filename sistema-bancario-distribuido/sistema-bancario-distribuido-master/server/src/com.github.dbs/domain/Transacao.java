package com.github.dbs.domain;

import com.github.dbs.enums.OperacaoEnum;

import java.math.BigDecimal;
import java.util.Date;

public class Transacao {
    private Integer id;
    private Conta contaOrigem;
    private Conta contaDestino;
    private String operacao;
    private BigDecimal valor;
    private Date dataOcorrencia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoEnum operacaoEnum) {
        this.operacao = operacaoEnum.getValor();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }
}
