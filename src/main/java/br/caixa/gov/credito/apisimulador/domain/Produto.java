package br.caixa.gov.credito.apisimulador.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Entity
@Data
@Table(name = "PRODUTO")
public class Produto {

    @Id
    @Column(name = "CO_PRODUTO")
    private Integer id;

    @NotNull
    @Column(name = "NO_PRODUTO", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "PC_TAXA_JUROS", nullable = false)
    private Double percentualTaxaJuros;

    @NotNull
    @Column(name = "NU_MINIMO_MESES", nullable = false)
    private Short numeroMinimoMeses;

    @Null
    @Column(name = "NU_MAXIMO_MESES")
    private Short numeroMaximoMeses;

    @NotNull
    @Column(name = "VR_MINIMO", nullable = false)
    private BigDecimal valorMinimo;

    @Null
    @Column(name = "VR_MAXIMO")
    private BigDecimal valorMaximo;

}
