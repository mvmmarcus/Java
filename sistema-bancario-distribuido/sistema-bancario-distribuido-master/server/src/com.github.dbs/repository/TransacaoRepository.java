package com.github.dbs.repository;

import com.github.dbs.builder.SqlBuilder;
import com.github.dbs.domain.Conta;
import com.github.dbs.domain.Transacao;
import com.github.dbs.enums.OperacaoEnum;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransacaoRepository extends AbstractRepository{
    public ResultSet create(OperacaoEnum operacao, Integer idConta, BigDecimal valor) {
        String sql = new SqlBuilder
                .Insert("transacao")
                .column("conta_origem_id", idConta.toString())
                .column("operacao", operacao.toString())
                .column("valor", valor.toString())
                .column("data_ocorrencia", new Date().toString())
                .buildString();

        return executeQuery(sql);
    }

    public ResultSet create(OperacaoEnum operacao, Integer idContaOrigem, Integer idContaDestino, BigDecimal valor) {
        String sql = new SqlBuilder
                .Insert("transacao")
                .column("conta_origem_id", idContaOrigem.toString())
                .column("conta_destino_id", idContaDestino.toString())
                .column("operacao", operacao.toString())
                .column("valor", valor.toString())
                .column("data_ocorrencia", new Date().toString())
                .buildString();

        return executeQuery(sql);
    }

    public List<Transacao> findAll(Conta conta) {
        List<Transacao> transacoes = new ArrayList<Transacao>();
        Transacao transacao = null;
        ContaRepository contaOrigem = new ContaRepository();
        ContaRepository contaDestino = new ContaRepository();

        String sql = new SqlBuilder
                .Select("*")
                .from("transacao")
                .where("conta_origem_id", "=", conta.getId())
                .buildString();
        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                transacao = new Transacao();
                transacao.setId(rs.getInt("id"));
                transacao.setContaOrigem(contaOrigem.findById(rs.getInt("conta_origem_id")));
                transacao.setContaDestino(contaDestino.findById(rs.getInt("conta_destino_id")));
                transacao.setOperacao(OperacaoEnum.valueOf(rs.getString("operacao")));
                transacao.setValor(rs.getBigDecimal("valor"));
                transacao.setDataOcorrencia(rs.getDate("data_ocorrencia"));
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return transacoes;
    }

    public List<Transacao> findAll(Conta conta, Date periodoInicial, Date periodoFinal) {
        List<Transacao> transacoes = new ArrayList<Transacao>();
        Transacao transacao = null;
        ContaRepository contaOrigem = new ContaRepository();
        ContaRepository contaDestino = new ContaRepository();

        String sql = new SqlBuilder
                .Select("*")
                .from("transacao")
                .where("conta_origem_id", "=", conta.getId())
                .where("data_ocorrencia", ">=", periodoInicial.toString())
                .where("data_ocorrencia", "<=", periodoFinal.toString())
                .buildString();
        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                transacao = new Transacao();
                transacao.setId(rs.getInt("id"));
                transacao.setContaOrigem(contaOrigem.findById(rs.getInt("conta_origem_id")));
                transacao.setContaDestino(contaDestino.findById(rs.getInt("conta_destino_id")));
                transacao.setOperacao(OperacaoEnum.valueOf(rs.getString("operacao")));
                transacao.setValor(rs.getBigDecimal("valor"));
                transacao.setDataOcorrencia(rs.getDate("data_ocorrencia"));
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return transacoes;
    }
}
