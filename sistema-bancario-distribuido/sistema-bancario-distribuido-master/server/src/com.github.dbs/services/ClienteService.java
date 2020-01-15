package com.github.dbs.services;

import com.github.dbs.domain.Cliente;
import com.github.dbs.repository.ClienteRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteService {

    private static final ClienteRepository clienteRepository = new ClienteRepository();

    public static Integer createAndGetId(Cliente cliente) {
        ResultSet resultSet = clienteRepository.create(cliente);

        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ignored) {
        }

        return null;
    }

    public static Cliente buscarCliente(String cpf) {
        return clienteRepository.find(cpf);
    }
}
