package br.caixa.gov.credito.apisimulador.application.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.caixa.gov.credito.apisimulador.domain.Simulacao;
import br.caixa.gov.credito.apisimulador.service.dto.SimulacaoResponseDTO;

@Mapper(componentModel = "spring", uses = {SistemaAmortizacaoMapper.class, ParcelaMapper.class})
public interface SimulacaoMapper {

    public static final SimulacaoMapper INSTANCE = Mappers.getMapper(SimulacaoMapper.class);
    
    @Mapping(target = "codigoProduto", source = "produto.id")
    @Mapping(target = "descricaoProduto", source = "produto.nome")
    @Mapping(target = "taxaJuros", source = "produto.percentualTaxaJuros")
    SimulacaoResponseDTO toSimulacaoResponseDto(Simulacao entity);
    Collection<SimulacaoResponseDTO> toSimulacaoResponseDtoList(Collection<Simulacao> entities);
    

}
