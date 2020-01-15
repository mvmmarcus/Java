package com.github.dbs.services;


import com.github.dbs.domain.Cliente;
import com.github.dbs.domain.Conta;
import com.github.dbs.enums.OperacaoEnum;
import com.github.dbs.repository.ContaRepository;
import com.github.dbs.repository.TransacaoRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ContaService {

    private static final ContaRepository contaRepository = new ContaRepository();
    private static final TransacaoRepository transacaoRepository = new TransacaoRepository();

    public static Integer createAndGetId(Cliente cliente, String senha) {
        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setSenha(criptografarSenha(senha));

        ResultSet resultSet = contaRepository.create(conta);

        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ignored) {
        }

        return null;
    }

    public static void sacar(Integer idConta, BigDecimal valor) {
        Conta conta = contaRepository.findById(idConta);
        conta.sacar(valor);

        contaRepository.update(idConta, conta);
        transacaoRepository.create(OperacaoEnum.SAQUE, idConta, valor);
    }

    public static void depositar(Integer idConta, BigDecimal valor) {
        Conta conta = contaRepository.findById(idConta);
        conta.depositar(valor);

        contaRepository.update(idConta, conta);
        transacaoRepository.create(OperacaoEnum.DESPOSITO, idConta, valor);
    }

    public static void transferir(Integer idContaOrigem, Integer idContaDestino, BigDecimal valor) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem);
        Conta contaDestino = contaRepository.findById(idContaDestino);

        contaOrigem.sacar(valor);
        contaDestino.depositar(valor);

        contaRepository.update(idContaOrigem, contaOrigem);
        contaRepository.update(idContaDestino, contaDestino);

        transacaoRepository.create(OperacaoEnum.TRANSFERENCIA, idContaOrigem, idContaDestino, valor);

    }

    public static BigDecimal consultaSaldo(Integer idConta) {
        Conta conta = contaRepository.findById(idConta);
        return Optional.ofNullable(conta).map(Conta::getSaldo).orElse(null);
    }

    public static Conta buscarConta(Integer idCliente) {
        return contaRepository.findByClienteId(idCliente);
    }

    public static Conta buscarContaPorId(Integer idConta) {
        return contaRepository.findById(idConta);
    }

    private static String criptografarSenha(String senha) {
        return DigestUtils.md5Hex(senha).toUpperCase();
    }
}
