package br.caixa.gov.credito.apisimulador.application.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.caixa.gov.credito.apisimulador.domain.Parcela;
import br.caixa.gov.credito.apisimulador.service.dto.ParcelaResponseDTO;

@Mapper(componentModel = "spring")
public interface ParcelaMapper {


    public static final ParcelaMapper INSTANCE = Mappers.getMapper(ParcelaMapper.class);

    ParcelaResponseDTO toParcelaResponseDto(Parcela parcela);
    Collection<ParcelaResponseDTO> toParcelaResponseDtoList(Collection<Parcela> parcelas);
}
