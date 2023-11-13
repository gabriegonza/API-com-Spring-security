package com.banco.repository;

import com.banco.domain.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BancoRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByContaId(long contaId);

    List<Transferencia> findByDataBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Transferencia> findByNomeOperadorTransacao(String nomeOperador);

    List<Transferencia> findByDataBetweenAndNomeOperadorTransacao(LocalDateTime dataInicial, LocalDateTime dataFinal, String nomeOperador);

    List<Transferencia> findByTipoIgnoreCase(String tipoLowerCase);

    List<Transferencia> findByData(LocalDateTime dataInicio);
}

