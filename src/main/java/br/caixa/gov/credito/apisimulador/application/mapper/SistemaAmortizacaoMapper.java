package br.caixa.gov.credito.apisimulador.application.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.caixa.gov.credito.apisimulador.domain.SistemaAmortizacao;
import br.caixa.gov.credito.apisimulador.service.dto.SistemaAmortizacaoResponseDTO;

@Mapper(componentModel = "spring", uses = {ParcelaMapper.class})
public interface SistemaAmortizacaoMapper {


    public static final SistemaAmortizacaoMapper INSTANCE = Mappers.getMapper(SistemaAmortizacaoMapper.class);

    SistemaAmortizacaoResponseDTO toSistemaArmotizacaoResponseDto(SistemaAmortizacao sistemaArmotizacao);
    Collection<SistemaAmortizacaoResponseDTO> toSistemaArmotizacaoResponseDtoList(Collection<SistemaAmortizacao> sistemaArmotizacoes);
}
