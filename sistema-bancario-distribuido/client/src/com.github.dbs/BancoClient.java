package com.github.dbs;

import com.github.dbs.interfaces.BancoInterface;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BancoClient {

    public static void main(String[] args) throws IllegalAccessException {
        BancoInterface obj;

        try {
            obj = getBancoInterface(args);

            obj.criarCliente("João", "11345678911", "Rua 210", "5563981918191", new Date());

            Integer idConta = obj.criarConta("11345678911", "123456");

            if (obj.acessarConta(idConta, "123456")) {
                obj.deposito(idConta, new BigDecimal(75));
                obj.saque(idConta, new BigDecimal(50));
            }

            List<HashMap<String, String>> extrato = obj.consultaExtrato(idConta);

            for (int i = 0; i < extrato.size(); i++) {
                System.out.println(extrato.get(i));
            }

            obj.criarCliente("Guilherme", "11343378911", "Rua 378", "5563941918191", new Date());
            Integer idContaGuilherme = obj.criarConta("11343378911", "123");

            if (obj.acessarConta(idContaGuilherme, "123")) {
                obj.transferencia(idConta, "11343378911", new BigDecimal(20));
            }
        }
        catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
    private static BancoInterface getBancoInterface(String[] ids) throws RemoteException, NotBoundException {
        int randomIndex = (int) Math.round(Math.random() * (ids.length - 1));
        Registry registry = LocateRegistry.getRegistry(ids[randomIndex], 2001);
        return (BancoInterface) registry.lookup("BancoServer");
    }
}
