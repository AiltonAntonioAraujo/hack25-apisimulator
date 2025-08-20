package br.caixa.gov.credito.apisimulador.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.caixa.gov.credito.apisimulador.domain.MetricaServico;
import br.caixa.gov.credito.apisimulador.service.TelemetriaService;

@RestController
@RequestMapping("/telemetria")
public class TelemetriaController {

    private final TelemetriaService telemetryService;

    public TelemetriaController(TelemetriaService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @GetMapping("/servico/{nomeServico}")
    public ResponseEntity<Map<String, Object>> getMetricasServico(
            @PathVariable String nomeServico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        
        Map<String, Object> metricas = telemetryService.getMetricasServico(nomeServico, inicio, fim);
        return ResponseEntity.ok(metricas);
    }

    @GetMapping("/servico/{nomeServico}/raw")
    public ResponseEntity<List<MetricaServico>> getMetricasRaw(
            @PathVariable String nomeServico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        
        List<MetricaServico> metricas = telemetryService.getMetricasRaw(nomeServico, inicio, fim);
        return ResponseEntity.ok(metricas);
    }

}