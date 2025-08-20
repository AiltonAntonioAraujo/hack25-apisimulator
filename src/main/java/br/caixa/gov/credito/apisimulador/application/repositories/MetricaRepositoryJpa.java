package br.caixa.gov.credito.apisimulador.application.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.caixa.gov.credito.apisimulador.domain.MetricaServico;

public interface MetricaRepositoryJpa extends JpaRepository<MetricaServico, Long> {
    
    List<MetricaServico> findByNomeServicoAndTimestampBetween(
            String nomeServico, LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT AVG(sm.responseTimeMs) FROM MetricaServico sm WHERE sm.nomeServico = ?1 AND sm.timestamp BETWEEN ?2 AND ?3")
    Double getAverageResponseTime(String nomeServico, LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT COUNT(sm) FROM MetricaServico sm WHERE sm.nomeServico = ?1 AND sm.timestamp BETWEEN ?2 AND ?3")
    Long getRequestCount(String nomeServico, LocalDateTime inicio, LocalDateTime fim);

    
}