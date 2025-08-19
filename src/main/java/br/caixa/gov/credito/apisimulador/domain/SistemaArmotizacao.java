package br.caixa.gov.credito.apisimulador.domain;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class SistemaArmotizacao {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_SISTEMA_AMORTIZACAO", nullable = false)
    private TipoSistemaAmrotizacao tipo;
    
    @NotNull
    @Embedded
    @Column(name = "PARCELAS", nullable = false)
    private Collection<Parcela> parcelas;

}
