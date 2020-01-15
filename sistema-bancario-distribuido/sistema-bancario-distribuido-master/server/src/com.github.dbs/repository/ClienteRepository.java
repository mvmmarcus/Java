package com.github.dbs.repository;

import com.github.dbs.builder.SqlBuilder;
import com.github.dbs.domain.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRepository extends AbstractRepository {

    public ResultSet create(Cliente cliente) {
        String sql = new SqlBuilder
                .Insert("cliente")
                .column("nome", cliente.getNome())
                .column("cpf", cliente.getCpf())
                .column("endereco", cliente.getEndereco())
                .column("telefone", cliente.getTelefone())
                .column("data_nascimento", cliente.getDataNascimento().toString())
                .buildString();

        return executeQuery(sql);
    }

    public ResultSet update(Integer id, Cliente cliente) {
        SqlBuilder.Update updateBuilder = new SqlBuilder.Update("cliente");

        if (cliente.getNome() != null) {
            updateBuilder.set("nome", cliente.getNome());
        }

        if (cliente.getCpf() != null) {
            updateBuilder.set("cpf", cliente.getCpf());
        }

        if (cliente.getEndereco() != null) {
            updateBuilder.set("endereco", cliente.getEndereco());
        }

        if (cliente.getTelefone() != null) {
            updateBuilder.set("telefone", cliente.getTelefone());
        }

        if (cliente.getDataNascimento() != null) {
            updateBuilder.set("data_nascimento", cliente.getDataNascimento().toString());
        }

        String sql = updateBuilder
                .where("id", "=", id)
                .buildString();

        return executeQuery(sql);
    }

    public Cliente findById(Integer id) {
        Cliente cliente = new Cliente();
        String sql = new SqlBuilder
                .Select("*")
                .from("cliente")
                .where("id", "=", id)
                .buildString();

        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return cliente;
    }

    public Cliente find(String cpf) {
        Cliente cliente = new Cliente();
        String sql = new SqlBuilder
                .Select("*")
                .from("cliente")
                .where("cpf", "=", cpf)
                .buildString();

        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        return cliente;
    }

    public void delete(Integer id) {
        String sql = new SqlBuilder
                .Delete()
                .from("cliente")
                .where("id", "=", id)
                .buildString();

        executeQuery(sql);
    }
}
