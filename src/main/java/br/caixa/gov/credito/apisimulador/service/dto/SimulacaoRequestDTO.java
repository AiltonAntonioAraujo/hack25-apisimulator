package br.caixa.gov.credito.apisimulador.service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

public record SimulacaoRequestDTO(
            @Digits(integer = 18, fraction = 2, message = "O valor desejado deve ser um valor válido") 
            BigDecimal valorDesejado,
            
            @Positive(message = "O prazo deve ser um número positivo")
            Integer prazo) {

}
