package br.caixa.gov.credito.apisimulador.application.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.caixa.gov.credito.apisimulador.domain.ServiceMetric;

public interface ServiceMetricRepositoryJpa extends JpaRepository<ServiceMetric, Long> {
    
    List<ServiceMetric> findByServiceNameAndTimestampBetween(
            String serviceName, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT AVG(sm.responseTimeMs) FROM ServiceMetric sm WHERE sm.serviceName = ?1 AND sm.timestamp BETWEEN ?2 AND ?3")
    Double getAverageResponseTime(String serviceName, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(sm) FROM ServiceMetric sm WHERE sm.serviceName = ?1 AND sm.timestamp BETWEEN ?2 AND ?3")
    Long getRequestCount(String serviceName, LocalDateTime start, LocalDateTime end);

    
}