package br.caixa.gov.credito.apisimulador.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Double valorMinimo;
    
    @Null
    @Column(name = "VR_MAXIMO")
    private Double valorMaximo;

}
