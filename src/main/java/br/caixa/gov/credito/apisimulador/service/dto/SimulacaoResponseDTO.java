package br.caixa.gov.credito.apisimulador.service.dto;

import java.util.Collection;

public record SimulacaoResponseDTO(Integer idSimulacao, Integer codigoProduto, String descricaoProduto, Double taxaJuros, Collection<SistemaAmortizacaoResponseDTO> resultadoSimulacao) {


}
