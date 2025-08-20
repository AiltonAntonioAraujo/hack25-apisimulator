package br.caixa.gov.credito.apisimulador.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class Parcela {
    @NotNull
    @Column(name = "NU_PARCELA", nullable = false)
    private Integer numero;
    
    @NotNull
    @Column(name = "VR_AMORTIZACAO", nullable = false)
    private BigDecimal valorAmortizacao;
    
    @NotNull
    @Column(name = "VR_JUROS", nullable = false)
    private BigDecimal valorJuros;
    
    @NotNull
    @Column(name = "VR_PRESTACAO", nullable = false)
    private BigDecimal valorPrestacao;

    
}
