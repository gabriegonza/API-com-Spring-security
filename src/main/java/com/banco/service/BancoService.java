package com.banco.service;

import com.banco.domain.Transferencia;
import com.banco.exception.BadRequestException;
import com.banco.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;


@Service
public class BancoService {
    @Autowired
    private BancoRepository bancoRepository;

    public List<Transferencia> findall() {
        return (List<Transferencia>) bancoRepository.findAll();
    }

    public List<Transferencia> getTransferenciasByIdConta(String contaId) {
        if (!contaId.matches("^\\d+$")) {
            throw new BadRequestException("Conta deve ser um número válido");
        }

        long contaIdLong = Long.parseLong(contaId); // Converte a entrada para um tipo long
        List<Transferencia> idExist = bancoRepository.findByContaId(contaIdLong);

        if (idExist.isEmpty()) {
            throw new BadRequestException("Conta inexistente");
        }
        return bancoRepository.findByContaId(contaIdLong);
    }

    public List<Transferencia> buscarTransferenciasPorData(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Transferencia> transferencias;
        if (dataFim != null) {
            transferencias = bancoRepository.findByDataBetween(dataInicio, dataFim);
        } else {
            transferencias = bancoRepository.findByData(dataInicio);
        }

        if (transferencias.isEmpty()) {
            throw new BadRequestException("Não há transferência para esse intervalo de data");
        }

        return transferencias;
    }

    public List<Transferencia> nomeOperadorTransacao(String nomeOperador) {
        String nameCase = nomeOperador.toUpperCase();
        List<Transferencia> nameExist = bancoRepository.findByNomeOperadorTransacao(nameCase);
        if (nameExist.isEmpty()) {
            throw new BadRequestException("Nome não cadastrado no banco");
        }
        return nameExist;
    }

    public List<Transferencia> buscarTransferenciasPorNomeEData(String nomeOperador, LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Transferencia> resultado;

        if (nomeOperador != null) {
            nomeOperador = nomeOperador.toUpperCase();
        }

        if (nomeOperador != null && dataInicio != null && dataFim != null) {
            resultado = bancoRepository.findByDataBetweenAndNomeOperadorTransacao(dataInicio, dataFim, nomeOperador);
        } else if (dataInicio != null && dataFim != null) {
            resultado = buscarTransferenciasPorData(dataInicio, dataFim);
        } else if (nomeOperador != null) {
            return nomeOperadorTransacao(nomeOperador);
        } else {
            return bancoRepository.findAll();
        }

        if (resultado.isEmpty()) {
            throw new BadRequestException("Nenhum resultado encontrado");
        }

        return resultado;
    }

    public List<Transferencia> findByTipo(String tipo) {
        String tipoLowerCase = tipo.toLowerCase();
        List<Transferencia> type = bancoRepository.findByTipoIgnoreCase(tipoLowerCase);

        if (type.isEmpty()) {
            throw new BadRequestException("Tipo não encontrado ou não existente");
        }
        return type;
    }
}
