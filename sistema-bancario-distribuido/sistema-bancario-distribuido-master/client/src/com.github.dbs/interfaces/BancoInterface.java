package com.github.dbs.interfaces;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface BancoInterface extends Remote {

    Integer criarCliente(String nome, String cpf, String endereco, String telefone, Date dtNascimento) throws RemoteException;

    Integer criarConta(String cpf, String senha) throws RemoteException;

    Boolean acessarConta(Integer numConta, String senha) throws RemoteException;

    BigDecimal consultaSaldo(Integer idConta) throws RemoteException;

    void saque(Integer idConta, BigDecimal valor) throws RemoteException;

    void deposito(Integer idConta, BigDecimal valor) throws RemoteException;

    void transferencia(Integer idConta, String cpfDestino, BigDecimal valor) throws RemoteException;

    List<HashMap<String, String>> consultaExtrato(Integer idConta) throws RemoteException;

    List<HashMap<String, String>> consultaExtratoPeriodo(Integer idConta, Date periodoInicial, Date periodoFinal) throws RemoteException;

}
