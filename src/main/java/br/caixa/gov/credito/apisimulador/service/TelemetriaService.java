package br.caixa.gov.credito.apisimulador.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.caixa.gov.credito.apisimulador.application.repositories.MetricaRepositoryJpa;
import br.caixa.gov.credito.apisimulador.domain.MetricaServico;

@Service
public class TelemetriaService {

    private final MetricaRepositoryJpa metricaRepository;

    public TelemetriaService(MetricaRepositoryJpa metricRepository) {
        this.metricaRepository = metricRepository;
    }

    public Map<String, Object> getMetricasServico(String nomeServico, LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Object> metrics = new HashMap<>();
        
        Long contadorRequisicoes = metricaRepository.getRequestCount(nomeServico, inicio, fim);
        Double mediaTempoResposta = metricaRepository.getAverageResponseTime(nomeServico, inicio, fim);
        
        metrics.put("nomeServico", nomeServico);
        metrics.put("periodo", Map.of("inicio", inicio, "fim", fim));
        metrics.put("volumeRequisicoes", contadorRequisicoes);
        metrics.put("tempoMedioEmMs", mediaTempoResposta);
        
        return metrics;
    }
    
    public List<MetricaServico> getMetricasRaw(String nomeServico, LocalDateTime inicio, LocalDateTime fim) {
        return metricaRepository.findByNomeServicoAndTimestampBetween(nomeServico, inicio, fim);
    }
    

}
