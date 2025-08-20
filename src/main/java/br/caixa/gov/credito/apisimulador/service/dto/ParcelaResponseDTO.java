package br.caixa.gov.credito.apisimulador.service.dto;

import java.math.BigDecimal;

public record ParcelaResponseDTO(
    Integer numero, 
    BigDecimal valorAmortizacao, 
    BigDecimal valorJuros, 
    BigDecimal valorPrestacao ) {
    }

