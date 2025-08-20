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

import br.caixa.gov.credito.apisimulador.domain.ServiceMetric;
import br.caixa.gov.credito.apisimulador.service.TelemetryService;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @GetMapping("/service/{serviceName}")
    public ResponseEntity<Map<String, Object>> getServiceMetrics(
            @PathVariable String serviceName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        
        Map<String, Object> metrics = telemetryService.getServiceMetrics(serviceName, start, end);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/service/{serviceName}/raw")
    public ResponseEntity<List<ServiceMetric>> getRawMetrics(
            @PathVariable String serviceName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        
        List<ServiceMetric> metrics = telemetryService.getRawMetrics(serviceName, start, end);
        return ResponseEntity.ok(metrics);
    }

}