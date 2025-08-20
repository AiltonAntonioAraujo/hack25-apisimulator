package br.caixa.gov.credito.apisimulador.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.caixa.gov.credito.apisimulador.application.repositories.ServiceMetricRepositoryJpa;
import br.caixa.gov.credito.apisimulador.domain.ServiceMetric;

@Service
public class TelemetryService {

    private final ServiceMetricRepositoryJpa metricRepository;

    public TelemetryService(ServiceMetricRepositoryJpa metricRepository) {
        this.metricRepository = metricRepository;
    }

    public Map<String, Object> getServiceMetrics(String serviceName, LocalDateTime start, LocalDateTime end) {
        Map<String, Object> metrics = new HashMap<>();
        
        Long requestCount = metricRepository.getRequestCount(serviceName, start, end);
        Double avgResponseTime = metricRepository.getAverageResponseTime(serviceName, start, end);
        
        metrics.put("serviceName", serviceName);
        metrics.put("period", Map.of("start", start, "end", end));
        metrics.put("requestVolume", requestCount);
        metrics.put("averageResponseTimeMs", avgResponseTime);
        
        return metrics;
    }
    
    public List<ServiceMetric> getRawMetrics(String serviceName, LocalDateTime start, LocalDateTime end) {
        return metricRepository.findByServiceNameAndTimestampBetween(serviceName, start, end);
    }
    

}
