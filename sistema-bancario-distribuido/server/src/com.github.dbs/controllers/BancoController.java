package com.github.dbs.controllers;

import com.github.dbs.interfaces.BancoInterface;
import com.github.dbs.domain.Cliente;
import com.github.dbs.domain.Conta;
import com.github.dbs.domain.Transacao;
import com.github.dbs.services.ClienteService;
import com.github.dbs.services.ContaService;
import com.github.dbs.services.TransacaoService;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BancoController extends UnicastRemoteObject implements BancoInterface {

    protected BancoController() throws RemoteException {
    }

    @Override
    public Integer criarCliente(String nome, String cpf, String endereco, String telefone, Date dtNascimento) throws RemoteException {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);
        cliente.setDataNascimento(dtNascimento);

        return ClienteService.createAndGetId(cliente);
    }

    @Override
    public Integer criarConta(String cpf, String senha) throws RemoteException {
        Cliente cliente = ClienteService.buscarCliente(cpf);
        return ContaService.createAndGetId(cliente, senha);
    }

    @Override
    public Boolean acessarConta(Integer idConta, String senha) throws RemoteException {
        Conta conta = ContaService.buscarContaPorId(idConta);
        String senhaCriptografada = DigestUtils.md5Hex(senha).toUpperCase();
        return senhaCriptografada.equals(conta.getSenha());
    }

    @Override
    public BigDecimal consultaSaldo(Integer idConta) throws RemoteException {
        return ContaService.consultaSaldo(idConta);
    }

    @Override
    public void saque(Integer idConta, BigDecimal valor) throws RemoteException {
        ContaService.sacar(idConta, valor);
    }

    @Override
    public void deposito(Integer idConta, BigDecimal valor) throws RemoteException {
        ContaService.depositar(idConta, valor);
    }

    @Override
    public void transferencia(Integer idConta, String cpfDestino, BigDecimal valor) throws RemoteException {
        Cliente cliente = ClienteService.buscarCliente(cpfDestino);
        Conta contaDestino = ContaService.buscarConta(cliente.getId());

        ContaService.transferir(idConta, contaDestino.getId(), valor);
    }

    @Override
    public List<HashMap<String, String>> consultaExtrato(Integer idConta) throws RemoteException {
        ArrayList<Transacao> transacoes = (ArrayList<Transacao>) TransacaoService.consultaExtrato(idConta);
        return getExtratoFromTransacoes(transacoes);
    }

    @Override
    public List<HashMap<String, String>> consultaExtratoPeriodo(Integer idConta, Date periodoInicial, Date periodoFinal) throws RemoteException {
        ArrayList<Transacao> transacoes = (ArrayList<Transacao>) TransacaoService.consultaExtratoPeriodo(idConta, periodoInicial, periodoFinal);
        return getExtratoFromTransacoes(transacoes);
    }

    private List<HashMap<String, String>> getExtratoFromTransacoes(ArrayList<Transacao> transacoes) {
        List<HashMap<String, String>> extrato = new ArrayList<>();

        for (Transacao transacao : transacoes) {
            HashMap<String, String> dadoTransacao = new HashMap<>();
            dadoTransacao.put("CONTA", transacao.getContaOrigem().getId().toString());
            if (transacao.getContaDestino().getId() != null) {
                dadoTransacao.put("CONTA_DESTINO", transacao.getContaDestino().getId().toString());
            }
            dadoTransacao.put("OPERACAO", transacao.getOperacao());
            dadoTransacao.put("VALOR", transacao.getValor().toString());
            dadoTransacao.put("DATA", transacao.getDataOcorrencia().toString());

            extrato.add(dadoTransacao);
        }
        return extrato;
    }

    public static void main(String[] args) {
        try {
            BancoController obj = new BancoController();
            Registry registry = LocateRegistry.createRegistry(2001);
            registry.rebind("BancoServer", obj);
            System.out.println("Servidor carregado no registry");
        } catch (Exception e) {
            System.out.println("Banco RMI erro: " + e.getMessage());
        }
    }
}
