package br.caixa.gov.credito.apisimulador.service.dto;

import java.util.Collection;

public record SistemaAmortizacaoResponseDTO(String tipo, Collection<ParcelaResponseDTO> parcelas) {


}
