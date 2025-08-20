package br.caixa.gov.credito.apisimulador.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SERVICE_METRIC")
public class ServiceMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "ENDPOINT")
    private String endpoint;

    @Column(name = "RESPONSE_TIME_MS")
    private Long responseTimeMs;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "REQUEST_METHOD")
    private String requestMethod;

    @Column(name = "STATUS_CODE")
    private Integer statusCode;

}
