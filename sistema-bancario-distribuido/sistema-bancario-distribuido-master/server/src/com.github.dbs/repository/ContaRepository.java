package com.github.dbs.repository;

import com.github.dbs.builder.SqlBuilder;
import com.github.dbs.domain.Cliente;
import com.github.dbs.domain.Conta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaRepository extends AbstractRepository {

    public ResultSet create(Conta conta) {
        String sql = new SqlBuilder
                .Insert("conta")
                .column("cliente_id", conta.getCliente().getId().toString())
                .column("senha", conta.getSenha())
                .buildString();

        return executeQuery(sql);
    }

    public Conta findById(Integer id) {
        Conta conta = new Conta();
        ClienteRepository clienteRepository = new ClienteRepository();
        String sql = new SqlBuilder
                .Select("*")
                .from("conta")
                .where("id", "=", id)
                .buildString();

        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                conta.setId(rs.getInt("id"));
                conta.setCliente(clienteRepository.findById(rs.getInt("cliente_id")));
                conta.setSaldo(rs.getBigDecimal("saldo"));
                conta.setSenha(rs.getString("senha"));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return conta;
    }

    public Conta findByClienteId(Integer idCliente) {
        Conta conta = new Conta();
        ClienteRepository clienteRepository = new ClienteRepository();
        String sql = new SqlBuilder
                .Select("*")
                .from("conta")
                .where("cliente_id", "=", idCliente)
                .buildString();

        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                conta.setId(rs.getInt("id"));
                conta.setCliente(clienteRepository.findById(rs.getInt("cliente_id")));
                conta.setSaldo(rs.getBigDecimal("saldo"));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return conta;
    }

    public ResultSet update(Integer id, Conta conta) {
        SqlBuilder.Update updateBuilder = new SqlBuilder.Update("conta");

        if (conta.getId() != null) {
            updateBuilder.set("id", conta.getId().toString());
        }

        if (conta.getCliente() != null) {
            updateBuilder.set("cliente_id", conta.getCliente().getId().toString());
        }

        if (conta.getSaldo() != null) {
            updateBuilder.set("saldo", conta.getSaldo().toString());
        }

        if (conta.getSenha() != null) {
            updateBuilder.set("senha", conta.getSenha());
        }

        String sql = updateBuilder
                .where("id", "=", id)
                .buildString();

        return executeQuery(sql);
    }
}
