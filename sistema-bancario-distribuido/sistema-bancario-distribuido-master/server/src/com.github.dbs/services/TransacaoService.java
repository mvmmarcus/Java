package com.github.dbs.services;

import com.github.dbs.domain.Transacao;
import com.github.dbs.enums.OperacaoEnum;
import com.github.dbs.repository.ContaRepository;
import com.github.dbs.repository.TransacaoRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransacaoService {

    private static final TransacaoRepository transacaoRepository = new TransacaoRepository();
    private static final ContaRepository contaRepository = new ContaRepository();

    public static List<Transacao> consultaExtrato(Integer idConta) {
        return transacaoRepository.findAll(contaRepository.findById(idConta));
    }

    public static List<Transacao> consultaExtratoPeriodo(Integer idConta, Date periodoInicial, Date periodoFinal) {
        return transacaoRepository.findAll(contaRepository.findById(idConta), periodoInicial, periodoFinal);
    }
}
